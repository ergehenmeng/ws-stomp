package com.eghm.websocket.dto;

import com.eghm.websocket.enums.ErrorCode;

/**
 * @author 殿小二
 * @date 2020/11/3
 */
public class RespBody<T> {

    private T data;

    private int code = 200;

    private String msg = "success";

    public static <T> RespBody<T> success() {
        return new RespBody<>();
    }

    public static <T> RespBody<T> success(T data) {
        RespBody<T> respBody = new RespBody<>();
        respBody.setData(data);
        return respBody;
    }

    public static <T> RespBody<T> error(String errorMsg) {
        return error(500, errorMsg);
    }

    public static <T> RespBody<T> error(ErrorCode code) {
        return error(code.getCode(), code.getMsg());
    }

    public static <T> RespBody<T> error(int code, String msg) {
        RespBody<T> respBody = new RespBody<>();
        respBody.setCode(code);
        respBody.setMsg(msg);
        return respBody;
    }


    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
