package com.lvr.ihave.web.controller;

import com.lvr.ihave.annotation.UserLoginToken;
import com.lvr.ihave.business.service.AddressService;
import com.lvr.ihave.business.service.GoodsService;
import com.lvr.ihave.business.service.OrdersService;
import com.lvr.ihave.business.service.UserService;
import com.lvr.ihave.constant.Constant;
import com.lvr.ihave.constant.StatusEnum;
import com.lvr.ihave.pojo.Goods;
import com.lvr.ihave.pojo.Orders;
import com.lvr.ihave.pojo.SysUser;
import com.lvr.ihave.util.JSONResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 订单管理相关接口
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private AddressService addressService;

    @Resource
    private UserService userService;

    @Resource
    private OrdersService ordersService;


    @UserLoginToken
    @PostMapping(value = "/payment")
    public JSONResult payment(Integer gid, Integer aid){
//        System.out.println("gid: "+gid+", addressId: " + aid);
        HashMap<String, Object> resultMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(aid != null){
            Goods good = goodsService.selectByPrimaryKey(gid);
            SysUser seller = userService.selectByPrimaryKey(good.getUserId());
            String payId = getOrderIdByTime();

            resultMap.put("good",good);
            resultMap.put("aid",aid);
            resultMap.put("payId",payId);
            resultMap.put("seller",seller);
        }else{
            JSONResult.fail(StatusEnum.NO_CONTENT.getCode(),"收货地址为空，请返回添加收货地址");
        }
        return JSONResult.success(Constant.SUCCESS_OPERATION, resultMap);
    }

    /**
     * 获取支付结果
     * @param gid
     * @param aid
     * @param userId
     * @param payid
     * @return
     */
    @UserLoginToken
    @PostMapping(value = "/payresult")
    public JSONResult payresult(Integer gid, Integer aid, String userId, String payid){
        System.out.println("gid: "+gid+", addressId: " + aid+",payid: "+payid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Orders order = new Orders();
        order.setUserId(userId);
        order.setGoodId(gid);
        order.setAddress("");
        order.setPayId(payid.trim());
        order.setStatus((byte)1);
        order.setCreateAt(sdf.format(new Date()));
        order.setOrderId(getOrderIdByTime().trim());
        System.out.println(order);

        ordersService.insert(order);

        goodsService.updateStatusByPrimaryKey(gid,(byte)3);
        Goods good = goodsService.selectByPrimaryKey(gid);

        Map<String,Object> map = new HashMap<>();
        map.put("order",order);
        map.put("good",good);

        return JSONResult.success(Constant.SUCCESS_DATA,  map);
    }


    /**
     * 随机生成订单号方法
     * @return
     */
    public static String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }


}
