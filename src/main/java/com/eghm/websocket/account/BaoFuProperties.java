package com.eghm.websocket.account;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author 殿小二
 * @date 2020/11/27
 */
@PropertySource("classpath:crypt/baofu.properties")
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

    /**
     * 机构号/平台号
     */
    @Value("${orgNo}")
    private String orgNo;

    /**
     * 商户号
     */
    @Value("${merchantNo}")
    private String merchantNo;

    /**
     * 终端号
     */
    @Value("${terminalNo}")
    private String terminalNo;

    /**
     * 拉起网关页面时的请求地址
     */
    @Value("${awakeUrl}")
    private String awakeUrl;

    /**
     * 企业开户成功异步通知地址
     */
    @Value("${enterpriseNotifyUrl}")
    private String enterpriseNotifyUrl;

    /**
     * 企业开户地址
     */
    @Value("${enterpriseOpenUrl}")
    private String enterpriseOpenUrl;

}
