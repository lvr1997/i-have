package com.lvr.ihave.web.controller;

import cn.hutool.jwt.JWTUtil;
import com.lvr.ihave.annotation.PassToken;
import com.lvr.ihave.annotation.UserLoginToken;
import com.lvr.ihave.business.service.*;
import com.lvr.ihave.constant.Constant;
import com.lvr.ihave.constant.StatusEnum;
import com.lvr.ihave.ex.FileUploadException;
import com.lvr.ihave.ex.PhoneNotFoundException;
import com.lvr.ihave.ex.UsernameTakenException;
import com.lvr.ihave.pojo.*;
import com.lvr.ihave.util.JSONResult;
import com.lvr.ihave.util.MD5;
import com.lvr.ihave.web.utils.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 前台用户相关接口
 */
@RestController
@RequestMapping("/front/user")
public class FrontUserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private CommentsService commentsService;

    @Resource
    private WantedService wantedService;

    @Resource
    private HomeController homeController;

    @Resource
    private GoodsService goodsService;

    @Resource
    private ImageService imageService;

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private AddressService addressService;

    @Resource
    private OrdersService ordersService;

    @Resource
    private ReportService reportService;

    @Resource
    private ImageUtil imageUtil;

    @PassToken
    @ResponseBody
    @PostMapping(value = "/forget")
    public JSONResult forgetPassword(HttpSession session,
            @RequestParam(value = "phone_number", required = false) String phone_number,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "captchaCode", required = false) String captchaCode) {
        String captchaCheckCode = (String) session.getAttribute(Constant.CAPTCHA_CODE_KEY);
        SysUser user = userService.getUserByPhone(phone_number);
        // 判断验证码
        if (captchaCheckCode.equals(captchaCode)) {
            // 判断手机号是否注册
            if (user != null) {
                // 执行修改手机用户对应的密码
                userService.updatePasswordByPrimaryKey(user.getId(), MD5.md5(password));
                // 返回修改成功
                return JSONResult.success(Constant.SUCCESS_OPERATION);
            } else {
                return JSONResult.fail(StatusEnum.NOT_FOUND.getCode(), Constant.PHONE_UNSINED);
            }
        } else {
            return JSONResult.fail(StatusEnum.FAIL.getCode(), Constant.VERIFY_CODE_NOT_CORRECT);
        }
    }

    @PassToken
    @ResponseBody
    @PostMapping(value = "/login")
    public JSONResult checkLogin(@RequestBody Map<String, String> loginData) {
        try {
            // 从Map中获取参数
            String phone = loginData.get("phone");
            String password = loginData.get("password");

            // 参数验证
            if (phone == null || phone.trim().isEmpty()) {
                return JSONResult.fail(StatusEnum.FAIL.getCode(), "手机号不能为空");
            }
            if (password == null || password.trim().isEmpty()) {
                return JSONResult.fail(StatusEnum.FAIL.getCode(), "密码不能为空");
            }

            // 通过手机号查询用户信息
            SysUser cur_user = userService.getUserByPhone(phone);
            // 判断用户信息
            if (cur_user != null) {
                // 用户信息存在
                String word = MD5.md5(password);
                if (cur_user.getPassword().equals(word)) {
                    // 密码正确，并设置登录信息
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cur_user.setLastLogin(sdf.format(new Date()));
                    userService.updateLastLoginByPrimaryKey(cur_user);
                    // 返回信息
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("userId", cur_user.getUserId());
                    // 生成签名
                    String token = JWTUtil.createToken(map, Constant.LOGIN_USER_KEY.getBytes());
                    return JSONResult.success(Constant.LOGIN_SUCCESS, token);
                } else {
                    // 密码不正确
                    return JSONResult.fail(StatusEnum.FAIL.getCode(), Constant.LOGIN_FAILED);
                }
            }
        } catch (PhoneNotFoundException e) {
            // 用户不存在
            // 返回失败数据
            return JSONResult.fail(StatusEnum.NOT_FOUND.getCode(), e.getMessage());
        } catch (Exception e) {
            // 其他异常
            logger.error("登录异常：" + e.getMessage(), e);
            return JSONResult.fail(StatusEnum.SYSTEM_ERROR.getCode(), "登录失败，请稍后重试");
        }
        return JSONResult.fail(StatusEnum.SYSTEM_ERROR.getCode(), StatusEnum.SYSTEM_ERROR.getMsg());
    }

    @PassToken
    @ResponseBody
    @PostMapping(value = "/checkPhone")
    public JSONResult checkPhone(@RequestParam(value = "phone", required = true) String phone) {
        SysUser user = userService.getUserByPhone(phone);
        if (user != null) {
            return JSONResult.success(Constant.PHONE_SINED);
        } else {
            return JSONResult.success(Constant.PHONE_UNSINED);
        }
    }

    @PassToken
    @ResponseBody
    @GetMapping(value = "/checkCode")
    public JSONResult checkCode(HttpSession session, @RequestParam("checkcode") String checkcode) {
        // 获取session中保存的验证码
        String checkCode = (String) session.getAttribute(Constant.CAPTCHA_CODE_KEY);

        if (checkCode.equals(checkcode)) {
            return JSONResult.success(Constant.VERIFY_CODE_CORRECT);
        } else {
            return JSONResult.fail(StatusEnum.FAIL.getCode(), Constant.VERIFY_CODE_NOT_CORRECT);
        }
    }

    @PassToken
    @PostMapping(value = "/register", consumes = "application/json")
    @ResponseBody
    public JSONResult handleRegister(@RequestBody SysUser user) {

        try {
            // 参数验证
            if (user == null) {
                return JSONResult.fail(StatusEnum.FAIL.getCode(), "请求参数不能为空");
            }
            if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
                return JSONResult.fail(StatusEnum.FAIL.getCode(), "手机号不能为空");
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                return JSONResult.fail(StatusEnum.FAIL.getCode(), "密码不能为空");
            }
            
            // 设置用户信息初始值
            user.setUsername(user.getPhone());
            user.setPassword(MD5.md5(user.getPassword()));
            user.setRoleId("STUDENT");
            // 保存用户信息
            userService.insert(user);
            // 返回注册结果信息
            return JSONResult.success(Constant.REGISTER_SUCCESS);
        } catch (UsernameTakenException e) {
            // 用户名已存在异常
            return JSONResult.fail(StatusEnum.FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            // 处理其他异常
            logger.error("用户注册异常：{}", e.getMessage(), e);
            return JSONResult.fail(StatusEnum.SYSTEM_ERROR.getCode(), "注册失败，请稍后重试");
        }

    }

    @UserLoginToken
    @ResponseBody
    @GetMapping(value = "received")
    public JSONResult received(@RequestParam("userId") String userId) {
        // 查找自己商品的回复
        List<Goods> goods = goodsService.getGoodsByUserId(userId);
        List<Comments> commentsList = new ArrayList<Comments>();
        for (Goods good : goods) {
            List<Comments> comments1 = commentsService.selectByGoodsId(good.getId());
            if (comments1 != null) {
                commentsList.addAll(comments1);
            }
        }
        // 留言对象（留言内容，留言物品）集合
        List<CommentsExtend> commentsExtendList = new ArrayList<CommentsExtend>();
        if (commentsList.size() > 0) {
            for (Comments comment : commentsList) {
                CommentsExtend commentsExtend = new CommentsExtend();
                SysUser user1 = userService.selectByPrimaryKey(comment.getUserId());
                Goods goods1 = goodsService.selectByPrimaryKey(comment.getGoodsId());
                commentsExtend.setGoods(goods1);
                commentsExtend.setUser(user1);
                commentsExtend.setComments(comment);
                commentsExtendList.add(commentsExtend);
            }
        }
        return JSONResult.success(Constant.SUCCESS_DATA, commentsExtendList);
    }

    @UserLoginToken
    @ResponseBody
    @GetMapping(value = "/published")
    public JSONResult published(@RequestParam("userId") String userId) {
        // 发布留言（发布人，留言物品，留言内容）集合
        List<CommentsExtend> commentsExtends = new ArrayList<CommentsExtend>();
        // 留言集合
        List<Comments> comments = commentsService.selectByUserKeyWithContent(userId);
        for (Comments comment : comments) {
            // 留言扩展类CommentsExtend(包含发布人，留言物品，留言内容)
            CommentsExtend commentsExtend = new CommentsExtend();
            SysUser user1 = userService.selectByPrimaryKey(comment.getUserId());
            Integer goodsId = comment.getGoodsId();
            Goods goods1 = goodsService.selectByPrimaryKey(goodsId);
            commentsExtend.setGoods(goods1);
            commentsExtend.setUser(user1);
            commentsExtend.setComments(comment);
            // 把所有的commentsExtend放到List集合
            commentsExtends.add(commentsExtend);
        }

        return JSONResult.success(Constant.SUCCESS_DATA, commentsExtends);
    }

    @UserLoginToken
    @ResponseBody
    @GetMapping(value = "/want")
    public JSONResult want(@RequestParam("userId") String userId) {
        List<Wanted> wantedList = wantedService.selectWantByUserId(userId);
        List<WantedExtend> wantedExtendList = new ArrayList<WantedExtend>();
        for (Wanted wanted : wantedList) {
            WantedExtend wantedExtend = new WantedExtend();
            GoodsExtend goodsExtend = new GoodsExtend();
            // 获取收藏的商品信息
            Goods good = goodsService.selectByPrimaryKey(wanted.getGoodId());
            if (good != null) {
                List<Image> images = imageService.selectByGoodsPrimaryKey(good.getId());
                goodsExtend.setFirstImage(images.get(0));
                goodsExtend.setGood(good);
                // 将信息包装在一起
                wantedExtend.setGoodsExtend(goodsExtend);
                wantedExtend.setWanted(wanted);
                wantedExtendList.add(wantedExtend);
            }
        }

        return JSONResult.success(Constant.SUCCESS_DATA, wantedExtendList);
    }

    @UserLoginToken
    @ResponseBody
    @PostMapping(value = "/feedback")
    public JSONResult feedback(@RequestParam(value = "content") String content, @RequestParam("userId") String userId) {
        Report report = new Report();
        report.setType(Constant.REPORT_FEEDBACK);
        report.setUserId(userId);
        report.setContent(content);
        reportService.insertForFeedback(report);

        return JSONResult.success(Constant.SUCCESS_OPERATION);
    }

    @ResponseBody
    @PostMapping(value = "/search", produces = "text/html;charset=UTF-8")
    public JSONResult search(@RequestParam(value = "keywords", required = true) String keywords) {
        // 通过闲置名称和闲置描述搜索闲置信息
        List<Goods> goodsList = goodsService.searchGoods(keywords, keywords, (byte) 1);
        List<GoodsExtend> goodsExtendList = homeController.handlerGoodsAndView(goodsList);

        return JSONResult.success(Constant.SUCCESS_DATA, goodsExtendList);
    }

    @UserLoginToken
    @ResponseBody
    @PostMapping(value = "/logout")
    public JSONResult logout(@RequestParam("user") SysUser user) {
        // 设置用户最后登录信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        user.setLastLogin(sdf.format(new Date()));
        userService.updateLastLoginByPrimaryKey(user);
        // 重定向到首页
        return JSONResult.success(Constant.SUCCESS_OPERATION);
    }

    @UserLoginToken
    @ResponseBody
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public JSONResult handleUserGoods(@RequestParam("user") String userId) {

        List<Goods> goodsList = goodsService.getGoodsByUserId(userId);
        List<GoodsExtend> goodsAndImage = homeController.handlerGoodsAndView(goodsList);

        // 计算收入 根据发布的物品id查询
        Double income = ordersService.getIncomeByUserId(userId);
        // 计算支出 根据当前用户id查询
        Double spend = ordersService.getSpendByUserId(userId);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("goodsAndImage", goodsAndImage);
        resultMap.put("income", income);
        resultMap.put("spend", spend);

        return JSONResult.success(Constant.SUCCESS_DATA, resultMap);
    }

    /**
     * 处理上传的用户头像 - 修复版本
     * 
     * @param request request对象
     * @param file    头像文件
     * @return 返回信息
     */
    @UserLoginToken
    @ResponseBody
    @PostMapping(value = "/avatar_upload")
    public JSONResult avatarUpload(HttpServletRequest request,
            @RequestParam("avatar") MultipartFile file) {

        logger.info("接收到头像上传请求");

        // 验证文件是否为空
        if (file == null || file.isEmpty()) {
            logger.warn("头像上传失败：文件为空");
            return JSONResult.fail(StatusEnum.FAIL.getCode(), "请选择要上传的头像文件");
        }

        logger.info("文件大小：{} bytes, 文件名：{}", file.getSize(), file.getOriginalFilename());

        try {
            // 使用ImageUtil工具类上传头像
            String avatarUrl = imageUtil.uploadImage(file, "avatar");

            logger.info("用户头像上传成功，图片URL：{}", avatarUrl);

            return JSONResult.success(Constant.UPLOAD_SUCCESS, avatarUrl);

        } catch (FileUploadException e) {
            logger.error("用户头像上传失败，错误：{}", e.getMessage());
            return JSONResult.fail(StatusEnum.FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("用户头像上传异常，错误：{}", e.getMessage(), e);
            return JSONResult.fail(StatusEnum.SYSTEM_ERROR.getCode(), "头像上传失败，请稍后重试");
        }
    }

    /**
     * 保存修改数据
     * 
     * @param user 用户信息
     * @return
     */
    @UserLoginToken
    @ResponseBody
    @PostMapping(value = "/save_userinfo")
    public JSONResult saveUserInfo(SysUser user) {

        if (user != null) {
            try {
                String url = user.getImgUrl();
                // 图片名称
                String newUrl = url.substring(url.lastIndexOf("/") + 1);
                user.setImgUrl(newUrl);
                user.setId(user.getId());
                userService.updateByPrimaryKeySelective(user);
                SysUser cur_user = userService.selectByPrimaryKey(user.getUserId());
                // 修改成功，返回提示
                return JSONResult.success(Constant.SUCCESS_OPERATION, cur_user);
            } catch (Exception e) {

                return JSONResult.fail(StatusEnum.SYSTEM_ERROR.getCode(), StatusEnum.SYSTEM_ERROR.getMsg());
            }
        } else {

            return JSONResult.fail(StatusEnum.EMPTY.getCode(), StatusEnum.EMPTY.getMsg());
        }

    }

    /**
     * 验证收藏
     * 
     * @param gid 闲置ID
     * @return
     */
    @UserLoginToken
    @ResponseBody
    @GetMapping(value = "/collect")
    public JSONResult collect(String userId, Integer gid) {

        // 判断用户是否登陆
        if (userId != null) {
            Wanted wanted = wantedService.selectWant(userId, gid);
            System.out.println(wanted);
            if (wanted == null) {
                // 用户未收藏该闲置，则进行收藏
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Wanted newWanted = new Wanted(1, userId, gid, sdf.format(new Date()));
                wantedService.insert(newWanted);

                return JSONResult.success(Constant.COLLECT);
            } else {
                // 用户已收藏过闲置，取消收藏
                wantedService.deleteWantedByGoodsId(gid);
                return JSONResult.success(Constant.COLLECT_CANCEL);
            }
        } else {
            // 用户未登录
            return JSONResult.fail(StatusEnum.EMPTY.getCode(), StatusEnum.EMPTY.getMsg());
        }

    }

    /**
     * 处理用户提交的留言
     * 
     * @param gid     闲置的ID信息
     * @param content 提交的里留言内容
     * @return
     */
    @UserLoginToken
    @ResponseBody
    @PostMapping(value = "/comment")
    public JSONResult commentPublish(String userId, Integer gid, String content) {
        try {
            // 设置留言信息
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Comments comments = new Comments(1, userId, gid, sdf.format(new Date()), content);
            commentsService.insert(comments);
            // 返回信息
            return JSONResult.success(Constant.SUCCESS_OPERATION);
        } catch (Exception e) {

            return JSONResult.fail(StatusEnum.NO_CONTENT.getCode(), StatusEnum.NO_CONTENT.getMsg());
        }

    }

    /**
     * 处理点击购买闲置请求
     * 
     * @param id 闲置id
     * @return
     */
    @UserLoginToken
    @ResponseBody
    @PostMapping(value = "/buy-good/{id}")
    public JSONResult buyGood(@PathVariable("id") Integer id, @RequestParam("userId") String userId) {
        Goods goods = goodsService.selectByPrimaryKey(id);
        List<Image> images = imageService.selectByGoodsPrimaryKey(id);
        List<Address> list = addressService.getAllAddressByUid(userId);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("goods", goods);
        resultMap.put("images", images);
        resultMap.put("listAddress", list);
        return JSONResult.success(Constant.SUCCESS_DATA, resultMap);
    }

    /**
     * 查看我的收货地址
     *
     * @param userId
     * @return
     */
    @UserLoginToken
    @ResponseBody
    @GetMapping(value = "/address")
    public JSONResult showAddress(String userId) {
        List<Address> addressList = addressService.getAllAddressByUid(userId);
        return JSONResult.success(Constant.SUCCESS_DATA, addressList);
    }

    /**
     * 查询购买闲置
     * 
     * @param user
     * @return
     */
    @UserLoginToken
    @ResponseBody
    @PostMapping(value = "/order_buy")
    public JSONResult order_buy(SysUser user) {
        List<OrdersExtend> ordersExtends = new ArrayList<OrdersExtend>();
        List<Orders> orders = ordersService.selectOrdersByUserId(user.getId());
        if (orders.size() > 0) {
            for (Orders order : orders) {
                if (order != null) {
                    OrdersExtend ordersExtend = new OrdersExtend();
                    Goods good = goodsService.selectByPrimaryKey(order.getGoodId());
                    List<Image> images = imageService.selectByGoodsPrimaryKey(good.getId());

                    ordersExtend.setGood(good);
                    ordersExtend.setImages(images);
                    ordersExtend.setUser(user);

                    ordersExtend.setOrders(order);
                    ordersExtends.add(ordersExtend);
                }
            }
        }

        return JSONResult.success(Constant.SUCCESS_DATA, ordersExtends);
    }

    /**
     * 查询出售的闲置
     * 
     * @param user
     * @return
     */
    @UserLoginToken
    @ResponseBody
    @PostMapping(value = "/published_order")
    public JSONResult published_order(SysUser user) {

        List<OrdersExtend> ordersExtends = new ArrayList<OrdersExtend>();

        List<Goods> goods = goodsService.getGoodsByUserId(user.getUserId());

        List<Orders> orders = new ArrayList<Orders>();

        for (Goods good : goods) {
            Orders order = ordersService.selectOrdersByGoodId(good.getId());
            orders.add(order);
        }
        if (orders.size() > 0) {
            for (Orders order : orders) {
                if (order != null) {
                    OrdersExtend ordersExtend = new OrdersExtend();
                    Goods good = goodsService.selectByPrimaryKey(order.getGoodId());
                    List<Image> images = imageService.selectByGoodsPrimaryKey(good.getId());
                    ordersExtend.setGood(good);
                    ordersExtend.setImages(images);
                    ordersExtend.setUser(user);
                    ordersExtend.setOrders(order);
                    ordersExtends.add(ordersExtend);
                }

            }
        }

        return JSONResult.success(Constant.SUCCESS_DATA, ordersExtends);
    }
}
