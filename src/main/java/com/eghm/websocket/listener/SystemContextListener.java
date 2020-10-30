package com.eghm.websocket.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 系统启动监听器
 *
 * @author Administrator
 */
@WebListener
public class SystemContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("cxtPath", context.getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
