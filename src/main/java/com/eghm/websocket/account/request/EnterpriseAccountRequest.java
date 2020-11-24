package com.eghm.websocket.account.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 企业开户
 * @author 二哥很猛
 */
@Setter
@Getter
@ToString(callSuper = true)
public class EnterpriseAccountRequest extends BaseAccountRequest<EnterpriseAccountRequest> {

    /**
     * 默认身份类型 营业执照
     */
    private static final String DEFAULT_CERTIFICATE_TYPE = "LICENSE";

    /**
     * 法人默认身份证类型
     */
    private static final String DEFAULT_CORPORATE_CERT_TYPE = "ID";

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否为个体户 默认false
     */
    private Boolean selfEmployed;

    /**
     * 法人姓名
     */
    private String corporateName;

    /**
     * 法人证件类型
     */
    private String corporateCertType = DEFAULT_CORPORATE_CERT_TYPE;

    /**
     * 法人身份证Id
     */
    private String corporateCertId;

    /**
     * 法人手机号码
     */
    private String corporateMobile;
}
