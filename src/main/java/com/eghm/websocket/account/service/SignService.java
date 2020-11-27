package com.eghm.websocket.account.service;

import com.eghm.websocket.account.enums.BusinessType;

/**
 * @author 殿小二
 * @date 2020/11/27
 */
public interface SignService {

    /**
     * 给指定对象进行签名
     * @param type 业务类型
     * @param object 签名类型
     * @return 签名字符串
     */
    String sign(BusinessType type, Object object);

    /**
     * 给指定对象进行签名
     * @param signField 签名排序字段
     * @param object 签名对象
     * @return 签名字符串
     */
    String sign(String[] signField, Object object);
}
