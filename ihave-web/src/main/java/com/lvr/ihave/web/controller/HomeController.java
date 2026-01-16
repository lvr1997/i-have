package com.lvr.ihave.web.controller;

import com.lvr.ihave.annotation.PassToken;
import com.lvr.ihave.business.service.CarouselService;
import com.lvr.ihave.business.service.CategoryService;
import com.lvr.ihave.business.service.GoodsService;
import com.lvr.ihave.business.service.ImageService;
import com.lvr.ihave.constant.Constant;
import com.lvr.ihave.pojo.*;
import com.lvr.ihave.util.JSONResult;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 前台首页相关查询接口
 */
@RestController
@RequestMapping("/goods")
public class HomeController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private ImageService imageService;

    @Resource
    private CategoryService catelogService;

    @Resource
    private CarouselService carouselService;



    /**
     * 首页显示商品，查询所有的商品，按照商品刷新时间排序
     * 左侧显示浏览量最多的商品数据，按照浏览数排序
     * 首页显示分类数据，放入分类菜单
     * @return
     */
    @PassToken
    @GetMapping(value = "/index")
    public JSONResult Home() {

        //所有商品
        List<GoodsExtend> goodsAndImage = null;

        //所有没有禁用的分类
        List<Catelog> categories = catelogService.getAllCatelogByStatus((byte)1);
        //所有没有被下架的闲置
        List<Goods> goodsList = goodsService.selectGoodsByStatusOrderByPolishTime((byte)1);
        //把图片和闲置信息绑定在一起
        goodsAndImage = handlerGoodsAndView(goodsList);

        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("goodsList", goodsAndImage);
        map.put("categories",categories);

        return JSONResult.success(Constant.SUCCESS_DATA, map);

    }


    /**
     * 处理商品和图片之间的关联
     * @param goodsList 商品图片集合
     * @return  商品图片集合
     */
    public List<GoodsExtend> handlerGoodsAndView(List<Goods> goodsList){
        List<GoodsExtend> goodsAndImage = new ArrayList<GoodsExtend>();
        for (int i = 0; i < goodsList.size() ; i++) {
            //将闲置信息和image信息封装到GoodsExtend类中s
            GoodsExtend goodsExtend = new GoodsExtend();
            Goods good = goodsList.get(i);
            List<Image> images = imageService.selectByGoodsPrimaryKey(good.getId());
            goodsExtend.setGood(good);
//            goodsExtend.setImages(images);
            goodsExtend.setFirstImage(images.get(0));
            goodsAndImage.add(i, goodsExtend);
        }
        return goodsAndImage;
    }

    /**
     * 查询该类商品
     * @param cid
     * 要求该参数不为空
     * @return
     * @throws Exception
     */
    @PassToken
    @GetMapping(value = "/catelog/{cid}")
    public JSONResult catelogGoods(@PathVariable("cid") Integer cid) throws Exception {
        List<Goods> goodsList = goodsService.selectByCatelogAndStatus(cid,(byte)1);
        System.out.println(goodsList);
//        Catelog catelog = catelogService.selectByPrimaryKey(cid);
//        List<Catelog> catelogs = catelogService.getAllCatelogByStatus((byte)1);
        List<GoodsExtend> goodsExtendList = handlerGoodsAndView(goodsList);

        return JSONResult.success(Constant.SUCCESS_DATA, goodsExtendList);
    }

}
