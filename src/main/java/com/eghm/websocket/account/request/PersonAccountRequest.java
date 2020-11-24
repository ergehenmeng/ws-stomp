package com.eghm.websocket.account.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 个人账户
 * @author 二哥很猛
 */
@Setter
@Getter
@ToString(callSuper = true)
public class PersonAccountRequest extends BaseAccountRequest<PersonAccountRequest> {

    /**
     * 默认身份类型 身份证:ID
     */
    private static final String DEFAULT_CERTIFICATE_TYPE = "ID";

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 银行卡号
     */
    private String cardNo;

    /**
     * 银行预留手机号
     */
    private String mobileNo;

    public PersonAccountRequest() {
        super.setAccType(1);
        super.setCertificateType(DEFAULT_CERTIFICATE_TYPE);
    }
}
