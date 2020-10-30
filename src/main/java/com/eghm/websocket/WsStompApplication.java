package com.eghm.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.servlet.annotation.WebListener;

/**
 * @author 二哥很猛
 */
@SpringBootApplication
@ServletComponentScan(basePackages = "com.eghm.websocket.listener")
public class WsStompApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsStompApplication.class, args);
    }

}
