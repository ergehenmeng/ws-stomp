package com.eghm.websocket.exception;

import com.eghm.websocket.enums.ErrorCode;

public class SystemException extends RuntimeException{

	private static final long serialVersionUID = -1437371644001065746L;
	
	/**
	 * 0 为正常 1为异常
	 */
	private int code = 500;

	public SystemException(String message){
		super(message);
	}

    public SystemException(ErrorCode errorCode) {
	    this(errorCode.getCode(), errorCode.getMsg());
    }

    private SystemException(int code, String message){
		super(message);
		this.code = code;
	}

    public int getCode() {
        return code;
    }
}
