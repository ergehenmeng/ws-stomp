package com.eghm.websocket.account.response;

import lombok.Data;

/**
 * 针对H5网关类的基类
 * @author 殿小二
 * @date 2020/11/27
 */
@Data
public class H5Response {

    /**
     * 成功的状态
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * 成功或失败状态
     */
    private String retCode;

    /**
     * 失败时的错误信息
     */
    private String retMsg;
}