package com.eghm.websocket.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * 系统启动监听器
 * @author Administrator
 *
 */
public class SytemContextListener implements ServletContextListener{

    @Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		context.setAttribute("cxtPath", context.getContextPath());
		
	}

    @Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}


}
