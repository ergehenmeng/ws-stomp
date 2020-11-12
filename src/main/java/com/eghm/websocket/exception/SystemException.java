package com.eghm.websocket.exception;

import com.eghm.websocket.enums.ErrorCode;

/**
 * @author 二哥很猛
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 0 为正常 1为异常
     */
    private int code;

    public SystemException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMsg());
    }

    private SystemException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
