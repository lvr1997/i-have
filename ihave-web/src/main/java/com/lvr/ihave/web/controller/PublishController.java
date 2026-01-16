package com.lvr.ihave.web.controller;

import com.lvr.ihave.annotation.UserLoginToken;
import com.lvr.ihave.business.service.*;
import com.lvr.ihave.constant.Constant;
import com.lvr.ihave.constant.StatusEnum;
import com.lvr.ihave.ex.FileUploadException;
import com.lvr.ihave.pojo.*;
import com.lvr.ihave.util.JSONResult;
import com.lvr.ihave.web.utils.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 闲置物品发布接口
 */
@RestController
@RequestMapping("/publish")
public class PublishController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserService userService;

    @Resource
    private CategoryService catelogService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private ImageService imageService;

    @Resource
    private ReportService reportService;

    @Resource
    private ImageUtil imageUtil;


    /**
     * 上传商品图片 - 修复版本
     * @param userId 用户ID
     * @param fileName 图片文件
     * @param request HTTP请求
     * @return 上传结果
     */
    @UserLoginToken
    @PostMapping(value = "/upload")
    public JSONResult uploadFile(@RequestParam("userId") String userId, 
                                @RequestParam("fileName") MultipartFile fileName, 
                                HttpServletRequest request) {
        
        logger.info("接收到商品图片上传请求，用户：{}", userId);
        
        // 验证文件是否为空
        if (fileName == null || fileName.isEmpty()) {
            logger.warn("商品图片上传失败：文件为空，用户：{}", userId);
            return JSONResult.fail(StatusEnum.FAIL.getCode(), "请选择要上传的图片文件");
        }
        
        logger.info("文件大小：{} bytes, 文件名：{}", fileName.getSize(), fileName.getOriginalFilename());
        
        try {
            // 使用ImageUtil工具类上传图片
            String imageUrl = imageUtil.uploadImage(fileName, "goods");
            
            logger.info("商品图片上传成功，用户：{}，图片URL：{}", userId, imageUrl);
            
            return JSONResult.success(Constant.UPLOAD_SUCCESS, imageUrl);
            
        } catch (FileUploadException e) {
            logger.error("商品图片上传失败，用户：{}，错误：{}", userId, e.getMessage());
            return JSONResult.fail(StatusEnum.FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("商品图片上传异常，用户：{}，错误：{}", userId, e.getMessage(), e);
            return JSONResult.fail(StatusEnum.SYSTEM_ERROR.getCode(), "图片上传失败，请稍后重试");
        }
    }

    /**
     * 删除上传的缓存商品图片 - 优化版本
     * @param fileName 图片名称
     * @return 删除结果
     */
    @UserLoginToken
    @PostMapping(value = "/delete_image")
    public JSONResult delectUploadFile(@RequestParam("fileName") String fileName) {
        try {
            // 构建完整的图片URL
            String imageUrl = imageUtil.getUrlPrefix() + "goods/" + fileName;
            
            // 使用ImageUtil删除图片
            boolean deleted = imageUtil.deleteImage(imageUrl, "goods");
            
            if (deleted) {
                logger.info("商品图片删除成功，文件名：{}", fileName);
                return JSONResult.success(Constant.SUCCESS_OPERATION);
            } else {
                logger.warn("商品图片删除失败，文件不存在：{}", fileName);
                return JSONResult.fail(StatusEnum.FAIL.getCode(), "文件不存在或删除失败");
            }
            
        } catch (Exception e) {
            logger.error("商品图片删除异常，文件名：{}，错误：{}", fileName, e.getMessage());
            return JSONResult.fail(StatusEnum.SYSTEM_ERROR.getCode(), "删除文件时发生错误");
        }
    }

    /**
     * 处理发布闲置
     * @param goods 闲置物品信息
     * @return
     */
    @UserLoginToken
    @PostMapping(value = "/complete")
    public JSONResult handlePublishComplete(@RequestParam("userId") String userId,
                                            @RequestParam("goods") Goods goods,
                                            @RequestParam("goodImages") String good_images){

        System.out.println("publish->CatelogId:"+goods.getCatelogId());

        //设置闲置初始信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        goods.setStartTime(sdf.format(new Date()));
        goods.setPolishTime(sdf.format(new Date()));
//        goods.setCommetNum(0);
        //获取用户信息
        SysUser user = userService.selectByPrimaryKey(userId);
        goods.setUserId(user.getUserId());
        goodsService.insert(goods);
        //插入闲置对应的图片信息
        good_images = good_images.substring(good_images.indexOf("\""),good_images.lastIndexOf("]"));
        String[] urls = good_images.split(",");
        for (String url:urls) {
            url = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("\""));
            Image image = new Image(1,goods.getId(),url);
            imageService.insert(image);
        }
        //更新用户闲置物品数量
//        userService.updateGoodsNum(user.getId(),user.getGoodsNum()+1);

        //返回响应json数据

        return JSONResult.success(Constant.SUCCESS_OPERATION);
    }

    @UserLoginToken
    @PostMapping(value = "/delete_good")
    public JSONResult delete_good(@RequestParam("gid") Integer gid){

        System.out.println("gid:"+gid);

        //根据闲置id更新闲置状态
        Goods goods = goodsService.selectByPrimaryKey(gid);
        Catelog catelog = catelogService.selectByPrimaryKey(goods.getCatelogId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        goodsService.updateStatusByPrimaryKey(gid,(byte)2);
        goodsService.updateEndTimeByPrimaryKey(gid,sdf.format(new Date()));

        return JSONResult.success(Constant.SUCCESS_OPERATION);
    }

    @UserLoginToken
    @PostMapping(value = "/reflash")
    public JSONResult reflash_good(@RequestParam("gid") Integer gid){

        //根据闲置id擦亮闲置信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        goodsService.updatePolishTimeByPrimaryKey(gid,sdf.format(new Date()));

        return JSONResult.success(Constant.SUCCESS_OPERATION);
    }

    /**
     *
     * @param gid 闲置物品id
     * @param good_title 闲置物品名称
     * @param description 举报描述
     * @return
     */
    @UserLoginToken
    @PostMapping(value = "/report")
    public JSONResult report(@RequestParam String userId,
                             @RequestParam Integer gid,
                             @RequestParam String good_title,
                             @RequestParam String description){
        System.out.println("gid:"+gid+", good_title:"+good_title+", description:"+description);

        Report report = new Report();
        report.setType(Constant.REPORT_TYPE);
        report.setGoodId(gid);
        report.setUserId(userId);
        report.setContent(description);
        reportService.insert(report);

        return JSONResult.success(Constant.SUCCESS_OPERATION);
    }

}
