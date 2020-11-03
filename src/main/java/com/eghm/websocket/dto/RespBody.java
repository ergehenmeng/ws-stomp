package com.eghm.websocket.dto;

import com.eghm.websocket.enums.ErrorCode;

/**
 * @author 殿小二
 * @date 2020/11/3
 */
public class RespBody<T> {

    private T date;

    private int code = 200;

    private String msg = "success";

    public static <T> RespBody<T> success() {
        return new RespBody<>();
    }

    public static <T> RespBody<T> success(T data) {
        RespBody<T> respBody = new RespBody<>();
        respBody.setDate(data);
        return respBody;
    }

    public static <T> RespBody<T> error(ErrorCode code) {
        RespBody<T> respBody = new RespBody<>();
        respBody.setCode(code.getCode());
        respBody.setMsg(code.getMsg());
        return respBody;
    }

    public void setDate(T date) {
        this.date = date;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getDate() {
        return date;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
