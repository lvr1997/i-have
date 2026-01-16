package com.lvr.ihave.constant;

public enum StatusEnum {
    FAIL(100, "失败"),
    SYSTEM_ERROR(101, "系统错误"),
    PARAM_ERROR(102, "参数错误"),
    EXCEED_MIN(103, "超过最小值"),
    EXCEED_MAX(104, "超过最大值"),
    EMPTY(105, "字段为空"),
    SUCCESS(200, "成功"),
    CREATED(201, "操作成功"),
    ACCEPTED(202, "已接收"),
    NOT_FOUND(203, "未找到"),
    NO_CONTENT(204, "没有内容"),
    FILE_UPLOAD_ERROR(205, "文件上传错误"),
    TOKEN_IS_EXPIRED(401, "未登录或登录状态已过期"),
    UNAUTHORIZED(403, "未授权"),
    AUTHORIZATION_INVALID(301, "授权无效");

    /**
     * 响应状态码
     */
    private final int code;

    /**
     * 响应提示
     */
    private final String msg;

    StatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
