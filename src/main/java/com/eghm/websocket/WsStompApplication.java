package com.eghm.websocket;

import com.eghm.websocket.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author 二哥很猛
 */
@SpringBootApplication
@MapperScan(basePackages = "com.eghm.websocket.mapper")
@Slf4j
public class WsStompApplication implements ApplicationListener<ContextRefreshedEvent>, SmartLifecycle {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(WsStompApplication.class).run(args);
    }
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        SpringUtil.setApplicationContext(context);
    }
    
    @Override
    public void start() {
        log.info("我启动了....");
    }
    
    @Override
    public void stop() {
    
    }
    
    @Override
    public boolean isRunning() {
        return false;
    }
}
