package com.eghm.websocket.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author 殿小二
 * @date 2020/11/27
 */
@PropertySource("classpath:baofu.properties")
@Component
@Data
public class BaoFuProperties {

    /**
     * 私钥
     */
    @Value("${privateKey}")
    private Resource privateKey;

    /**
     * 公钥
     */
    @Value("${publicKey}")
    private String publicKey;
    /**
     * 秘钥
     */
    @Value("${password}")
    private String password;

}
