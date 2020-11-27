package com.eghm.websocket.account.constant;

/**
 * @author 殿小二
 * @date 2020/11/27
 */
public class SignConstant {

    /**
     * 拉起H5页面时的验签
     */
    public static final String[] AWAKE_SIGN = new String[]{"orgNo", "merchantNo", "terminalNo", "callType", "loginNo", "requestDate", "dataContent"};

    /**
     * 企业开户时的签名
     */
    public static final String[] ENTERPRISE_SIGN = new String[]{"orgNo", "terminalNo", "loginNo", "requestDate", "email"};
}
