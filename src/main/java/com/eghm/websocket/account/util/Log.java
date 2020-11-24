/**
 * @author Administrator
 *
 */
package com.eghm.websocket.account.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class Log {
	
	public static void  Write(String msg) {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "\t: " + msg);
	}
	
	
	public static void getAllParamLog(HttpServletRequest Request){		
		String paraStr=null;
		String TmpStr = null;
		
		
		Enumeration<String> ehead = Request.getHeaderNames();	
		while(ehead.hasMoreElements()) {
			paraStr = ehead.nextElement();
			if(paraStr != null){
				TmpStr = "Key:"+paraStr+",Value:"+Request.getHeader(paraStr);
			}
			Log.Write("Header接收参数："+TmpStr);
		}
		
		
		Enumeration<String> e = Request.getParameterNames();	
		while(e.hasMoreElements()) {
			paraStr = e.nextElement();
			if(paraStr != null){
				TmpStr = "Key:"+paraStr+",Value:"+Request.getParameter(paraStr);
			}
			Log.Write("接收参数："+TmpStr);
		}
		
	}
}