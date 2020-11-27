package com.eghm.websocket.account.request;

import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/11/27
 */
@Data
public class PersonOpenAccountRequest {

    private String orgNo;

    private String merchantNo;

    private String terminalNo;

    private String loginNo;

    private String requestDate;

    private String customerName;

    private String certificateNo;

    private String base64imageFront;

    private String base64imageBack;

    private String signData;
}
