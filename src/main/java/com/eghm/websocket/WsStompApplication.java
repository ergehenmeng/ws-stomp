package com.eghm.websocket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 二哥很猛
 */
@SpringBootApplication
@MapperScan(basePackages = "com.eghm.websocket.mapper")
public class WsStompApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsStompApplication.class, args);
    }

}
