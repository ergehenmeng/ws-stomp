package com.eghm.websocket.account.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 二哥很猛
 * @param <T>
 */
@Getter
@Setter
@ToString
public class BaseAccountRequest<T> {

    private static final String DEFAULT_VERSION = "4.0.0";

    /**
     * 请求流水号 全局唯一
     */
    private String transSerialNo;

    /**
     * 版本号
     */
    private String version = DEFAULT_VERSION;

    /**
     * 账户类型 1:个人 2:商户
     */
    private Integer accType;

    /**
     * 默认一个
     */
    @JsonProperty("accInfo")
    private List<T> accountList;

    /**
     * 证件类型 个人:身份证ID  企业:营业执照LICENSE
     */
    private String certificateType;

    /**
     * 证件号码 个人:身份证号码 企业:营业执照编号
     */
    private String certificateNo;

    /**
     * 客户名称 个人:姓名 企业:营业执照上的名称
     */
    private String customerName;

    /**
     * 登陆号 个人:手机号码 商户:邮箱
     */
    private String loginNo;
}
