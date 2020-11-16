package com.eghm.websocket.utils;

import com.eghm.websocket.enums.ErrorCode;
import com.eghm.websocket.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

/**
 * @author 殿小二
 * @date 2020/11/16
 */
@Slf4j
public class SpringUtil {

    private SpringUtil() {
    }

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        verifyApplication();
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> cls) {
        verifyApplication();
        return applicationContext.getBean(beanName, cls);
    }

    public static <T> T getBean(Class<T> requiredType) {
        verifyApplication();
        return applicationContext.getBean(requiredType);
    }

    private static void verifyApplication() {
        if (applicationContext == null) {
            throw new SystemException(ErrorCode.SPRING_NOT_INIT);
        }
    }
}
