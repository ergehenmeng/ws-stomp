package com.eghm.websocket.account.enums;

/**
 * @author 殿小二
 * @date 2020/11/27
 */
public enum BusinessType {

    /**
     * C端开户
     */
    PERSON_OPEN_ACCOUNT(new String[]{"orgNo", "merchantNo","terminalNo","loginMobile","customerName","certificateNo"}),
    ;

    /**
     * 签名字段 按顺序排序
     */
    private String[] signField;


    BusinessType(String[] signField) {
        this.signField = signField;
    }

    public String[] getSignField() {
        return signField;
    }
}
