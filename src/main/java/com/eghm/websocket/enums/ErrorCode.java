package com.eghm.websocket.enums;

/**
 * @author 殿小二
 * @date 2020/11/3
 */
public enum ErrorCode {

    /**
     * 文档密码输入错误
     */
    DOC_PWD_ERROR(600, "文档密码输入错误"),

    /**
     * 手机号码格式错误
     */
    MOBILE_ERROR(601, "手机号码格式错误"),

    /**
     * 账号信息不存在
     */
    USER_FOUND_ERROR(602, "账号信息不存在"),

    /**
     * 账号被锁定
     */
    USER_LOCK_ERROR(603, "账号已锁定,请联系管理员"),

    /**
     * 账号或密码错误
     */
    USER_PWD_ERROR(603, "账号或密码错误"),

    /**
     * 用户登陆已过期
     */
    LOGIN_TIMEOUT(604, "用户登陆已过期"),

    /**
     * 未获取到本机ip
     */
    UNKNOWN_HOST_ADDRESS(605, "未获取到本机ip"),

    /**
     * 非本人文档禁止修改
     */
    ILLEGAL_MODIFY(606, "非本人文档禁止修改"),

    /**
     * Spring尚未初始化
     */
    SPRING_NOT_INIT(607, "Spring尚未初始化"),

    ;

    private int code;

    private String msg;

    ErrorCode(int code, String msg) {
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
