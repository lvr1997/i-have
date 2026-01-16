package com.lvr.ihave.web.controller;

import com.lvr.ihave.annotation.PassToken;
import com.lvr.ihave.business.service.*;
import com.lvr.ihave.constant.Constant;
import com.lvr.ihave.pojo.*;
import com.lvr.ihave.util.JSONResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.*;

/**
 * 闲置物品接口
 */
@Controller
@RequestMapping("/good")
public class GoodsController {
    @Resource
    private WantedService wantedService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private ImageService imageService;

    @Resource
    private CategoryService catelogService;

    @Resource
    private UserService userService;

    /**
     * 根据商品名称查询商品列表
     * @param keyword
     * @return
     * @throws Exception
     */
    @PassToken
    @GetMapping(value = "/listForAdmin")
    public String getGoodsListForAdmin(@RequestParam(value = "keyword",required = false) String keyword, Model model) throws Exception {
        List<Goods> goodsList = goodsService.searchGoodsByKeyWord(keyword);
        //返回数据
        model.addAttribute("goodsList", goodsList);
        return "goods/list";
    }

    @PassToken
    @GetMapping(value = "/list")
    public JSONResult getGoodsList(@RequestParam(value = "keyword",required = false) String keyword) throws Exception {
        List<Goods> goodsList = goodsService.searchGoodsByKeyWord(keyword);
        //返回数据
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("goodsList", goodsList);
        return JSONResult.success(Constant.SUCCESS_DATA, resultMap);
    }

    /**
     * 根据商品id查询该商品详细信息
     * @param goodId
     * @return
     * @throws Exception
     */
    @PassToken
    @GetMapping(value = "/detail")
    public JSONResult getGoodsById(@RequestParam("goodId") Integer goodId,
                                   @RequestParam(value = "userId") String userId) throws Exception {

        //判断当前商品是否被当前登入用户收藏
        Wanted wanted = null;
        if(userId!=null && userId != ""){
            wanted = wantedService.selectWant(userId,goodId);
        }

        //找出当前闲置
        Goods goodsFind = goodsService.selectByPrimaryKey(goodId);
        goodsService.updateViewCountByPrimaryKey(goodId,goodsFind.getViewCount()+1);
        Goods goods = goodsService.selectByPrimaryKey(goodId);
        //找出当前商品的用户信息
        SysUser seller = userService.selectByPrimaryKey(goods.getUserId());
        //找出分类信息
        Catelog catelog = catelogService.selectByPrimaryKey(goods.getCatelogId());
        //找到闲置对应的图片信息
        GoodsExtend goodsExtend = new GoodsExtend();
        List<Image> imageList = imageService.selectByGoodsPrimaryKey(goodId);
        goodsExtend.setGood(goods);
        goodsExtend.setImages(imageList);
        //返回数据
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("good", goodsExtend);
        resultMap.put("wanted",wanted);
        resultMap.put("seller", seller);
        resultMap.put("catelog", catelog);

        return JSONResult.success(Constant.SUCCESS_DATA, resultMap);
    }

}
