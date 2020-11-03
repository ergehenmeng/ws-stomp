package com.eghm.websocket.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.eghm.websocket.model.User;

/**
 * 常量类
 * @author Administrator
 *
 */
public class CommonConstant {
	
	/**
	 * 字符集 utf-8
	 */
	public static final Charset UTF8 = StandardCharsets.UTF_8;

    /**
     * 未知ip地址
     */
    public static final String UNKNOWN = "unknown";
	
	/**
	 * Http post请求
	 */
	public static final String POST = "POST";
	
	/**
	 * Http get请求
	 */
	public static final String GET = "GET";
	
	/**
	 * 登陆验证码
	 */
	public static final String AUTH_CODE = "autoCode";
	
	/**
	 * Session中存取的对象
	 */
	public static final String SESSION_USER = "loginUser";
	
	/**
	 * 排序方式
	 */
	public static final String ORDER_BY = "orderBy";
	
	/**
	 * 是否隐藏
	 */
	public static final String HIDDEN = "hidden";
	
	public static final  Map<String,User> WEBSOCKET_SESSION = new HashMap<String,User>();
	
}
