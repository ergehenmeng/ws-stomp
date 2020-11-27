package com.eghm.websocket.account.request;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.format.FastDateFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author 殿小二
 * @date 2020/11/27
 */
@Data
public class PersonOpenAccountRequest {

    /**
     * 机构号/平台号
     */
    private String orgNo;

    /**
     * 商户号
     */
    private String merchantNo;

    /**
     * 终端号
     */
    private String terminalNo;

    /**
     * 登陆号
     */
    private String loginNo;

    /**
     * 请求日期
     */
    private String requestDate = DatePattern.PURE_DATETIME_FORMAT.format(new Date());;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 身份证号
     */
    private String certificateNo;

    /**
     * 身份证头像面
     */
    private String base64imageFront;

    /**
     * 身份证国徽面
     */
    private String base64imageBack;

    /**
     * 签名
     */
    private String signData;
}
