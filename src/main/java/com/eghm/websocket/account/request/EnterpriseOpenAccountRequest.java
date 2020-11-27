package com.eghm.websocket.account.request;

import cn.hutool.core.date.DatePattern;
import lombok.Data;

import java.util.Date;

/**
 * @author 殿小二
 * @date 2020/11/27
 */
@Data
public class EnterpriseOpenAccountRequest {

    /**
     * orgNo
     */
    private String orgNo;

    /**
     * 终端号
     */
    private String terminalNo;

    /**
     * 请求时间
     */
    private String requestDate = DatePattern.PURE_DATETIME_FORMAT.format(new Date());

    /**
     * 用于接收注册邮件
     */
    private String email;

    /**
     * 用户在商户平台上注册的唯一编号
     */
    private String loginNo;

    /**
     * 签名数据
     */
    private String signData;

    /**
     * 开户成功回调地址
     */
    private String notifyUrl;
}
