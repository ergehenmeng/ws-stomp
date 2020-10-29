package com.eghm.websocket.exception;

public class SystemException extends RuntimeException{

	private static final long serialVersionUID = -1437371644001065746L;
	
	/**
	 * 0 为正常 1为异常
	 */
	private int type;
	
	public SystemException(){
		super();
	}
	
	public SystemException(String message){
		super(message);
	}
	
	public SystemException(String message ,int type){
		super(message);
		this.type = type;
	}
	
	public SystemException(String message,RuntimeException exception){
		super(message,exception);
	}

	public int getType() {
		return type;
	}
	
}
