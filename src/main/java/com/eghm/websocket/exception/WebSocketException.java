package com.eghm.websocket.exception;

import com.eghm.websocket.enums.ErrorCode;

/**
 * 针对websocket的异常
 * @author 殿小二
 * @date 2020/11/4
 */
public class WebSocketException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 0 为正常 1为异常
     */
    private int code;

    public WebSocketException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMsg());
    }

    private WebSocketException(int code, String message){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
