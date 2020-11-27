package com.eghm.websocket.account.service;

/**
 * @author 殿小二
 * @date 2020/11/27
 */
public interface HttpClientService {

    /**
     * post请求
     * @param url 请求地址
     * @param request 请求参数 对象或map等
     * @return 响应信息
     */
    String doPost(String url, Object request);
}
