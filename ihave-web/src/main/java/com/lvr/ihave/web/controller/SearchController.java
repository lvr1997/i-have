package com.lvr.ihave.web.controller;

import com.lvr.ihave.annotation.PassToken;
import com.lvr.ihave.annotation.UserLoginToken;
import com.lvr.ihave.business.service.*;
import com.lvr.ihave.pojo.*;
import com.lvr.ihave.util.JSONResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * 搜索请求的控制器
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Resource
    private UserService userService;

    @Resource
    private ReportService reportService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private ImageService imageService;

    @Resource
    private CommentsService commentsService;

    @Resource
    private CategoryService catelogService;

    @Resource
    private CarouselService carouselService;

    @Resource
    private OrdersService ordersService;

    @Resource
    private AddressService addressService;

    /**
     * 搜索用户
     * @param username
     * @return
     */
    @PassToken
    @GetMapping(value = "/searchuser")
    public JSONResult searchUser(String username){
        List<SysUser> userList= userService.searchUserByNameOrPhone(username,username);

        return JSONResult.success("获取成功", userList);
    }

    /**
     * 搜索闲置
     * @param keyword
     * @return
     */
    @PassToken
    @GetMapping(value = "/searchgood")
    public JSONResult searchGood(String keyword){
        List<CatelogExtend> catelogExtendList = new ArrayList<CatelogExtend>();
        List<Goods> goodsList = goodsService.searchGoodsByKeyWord(keyword);
        for (Goods goods:goodsList) {
            Catelog catelog = catelogService.selectByPrimaryKey(goods.getCatelogId());
            SysUser user = userService.selectByPrimaryKey(goods.getUserId());
            List<Image> images = imageService.selectByGoodsPrimaryKey(goods.getId());
            CatelogExtend catelogExtend = new CatelogExtend(goods,catelog,user,images);
            catelogExtendList.add(catelogExtend);
        }
        return JSONResult.success("获取成功", catelogExtendList);
    }

    /**
     * 搜索分类
     * @param keyword
     * @return
     */
    @PassToken
    @GetMapping("/searchcatelog")
    public JSONResult searchCatelog(String keyword){
        System.out.println("keyword: "+keyword);
        List<Catelog> catelogs = catelogService.searchCatelogByName(keyword);
        return JSONResult.success("获取成功", catelogs);
    }

    /**
     * 搜索举报物品信息
     * @param keyword
     * @return
     */
    @UserLoginToken
    @GetMapping("/searchreport")
    public JSONResult searchReport(String keyword){
        List<Report> reportList= reportService.selectAll();
        List<ReportExtend> reportExtendList = new ArrayList<ReportExtend>();
        for (Report report:reportList) {
            Goods goods = goodsService.searchGoodsByKeyWordAndPrimary(report.getGoodId(),keyword,keyword);
            if(goods!=null){
                List<Image> images = imageService.selectByGoodsPrimaryKey(goods.getId());
                SysUser user = userService.selectByPrimaryKey(report.getUserId());
                ReportExtend reportExtend = new ReportExtend(report,goods,images,user);
                reportExtendList.add(reportExtend);
            }
        }
        return JSONResult.success("获取成功", reportExtendList);
    }

    /**
     * 搜索评论
     * @param keyword
     * @return
     */
    @UserLoginToken
    @GetMapping("/searchcomments")
    public JSONResult searchComments(String keyword){
        List<Comments> comments = commentsService.searchCommentsByContent(keyword);
        List<CommentsExtend> commentsExtendList = new ArrayList<CommentsExtend>();
        for(Comments comment:comments){
            CommentsExtend commentsExtend = new CommentsExtend();
            Goods goods = goodsService.selectByPrimaryKey(comment.getGoodsId());
            SysUser user = userService.selectByPrimaryKey(comment.getUserId());
            commentsExtend.setUser(user);
            commentsExtend.setGoods(goods);
            commentsExtend.setComments(comment);
            commentsExtendList.add(commentsExtend);
        }
        return JSONResult.success("获取成功", commentsExtendList);
    }

    @UserLoginToken
    @GetMapping(value = "/searchorder")
    public JSONResult order(HttpSession session, String keyword){
        System.out.println("keyWord: "+keyword);
        List<OrdersExtend> ordersExtends = new ArrayList<OrdersExtend>();
        List<Orders> orders = ordersService.selectAll();
        for(Orders order: orders){
            OrdersExtend ordersExtend = new OrdersExtend();
            Goods goods = goodsService.selectByPrimaryKey(order.getGoodId());
            List<Image> images = imageService.selectByGoodsPrimaryKey(goods.getId());
            SysUser user = userService.selectByPrimaryKey(order.getUserId());
            ordersExtend.setUser(user);
            ordersExtend.setOrders(order);
            ordersExtend.setImages(images);
            ordersExtend.setGood(goods);
            ordersExtends.add(ordersExtend);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ordersExtends",ordersExtends);
        return JSONResult.success("获取成功", ordersExtends);
    }

}
