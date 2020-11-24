package com.eghm.websocket.account.request;

import cn.hutool.core.date.DatePattern;
import com.eghm.websocket.account.enums.CallType;
import lombok.Data;

import java.util.Date;

@Data
public class CallRequest {

    /**
     * 宝付提供 机构号
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
     * 登录号
     */
    private String loginNo;

    /**
     * 请求日期
     */
    private String requestDate = DatePattern.PURE_DATETIME_FORMAT.format(new Date());

    /**
     * 唤起类型
     */
    private CallType callType;

    /**
     * 其他参数
     */
    private String dataContent;

    /**
     * 签名信息
     */
    private String signData;
}
