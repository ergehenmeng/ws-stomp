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
