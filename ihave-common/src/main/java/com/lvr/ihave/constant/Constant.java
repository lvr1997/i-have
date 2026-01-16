package com.lvr.ihave.constant;

public class Constant {

    /**
     * 登录认证相关
     */
    public static final String LOGIN_USER_KEY = "ihave";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN = "Authorization";
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:"; //验证码 redis key
    public static final String LOGIN_TOKEN_KEY = "login_tokens:"; //登录用户 redis key
    public static final int EXPIRE_TIME = 60 * 5; //token过期时间

    public static final String ROLE_ADMIN = "AUDITOR,SUPERMANAGER,ADMIN";
    public static final String ROLE_STUDENT = "STUDENT";


    /**
     * 操作提示类型的常量
     */
    public static final String SUCCESS_DATA = "获取数据成功";
    public static final String SUCCESS_OPERATION = "操作成功";
    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String LOGIN_FAILED = "登录失败,密码错误";
    public static final String PHONE_UNSINED = "手机号未注册";
    public static final String PHONE_SINED = "手机号已注册";
    public static final String VERIFY_CODE_CORRECT = "验证码正确";
    public static final String VERIFY_CODE_NOT_CORRECT = "验证码不正确";
    public static final String REGISTER_SUCCESS = "注册成功";
    public static final String UPLOAD_ERROR = "上传失败";
    public static final String UPLOAD_SUCCESS = "上传成功";
    public static final String UPLOAD_FILE_TYPE_ERROR = "上传文件类型错误";
    public static final String COLLECT = "收藏成功";
    public static final String COLLECT_CANCEL = "取消收藏";

    /**
     * 业务相关
     */
    public static final int REPORT_TYPE = 1; //反馈类型为投诉
    public static final int REPORT_FEEDBACK = 2; //反馈类型为建议

    /**
     * 用户状态
     */

    public static final int USER_DEL_STATUS = 0; //用户已删除
    public static final int USER_USED_STATUS = 1; //用户可用
    public static final int USER_FREEZE_STATUS = 2; //用户已冻结




}
