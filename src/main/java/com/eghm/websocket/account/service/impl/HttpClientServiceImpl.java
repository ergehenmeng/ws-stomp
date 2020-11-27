package com.eghm.websocket.account.service.impl;

import cn.hutool.http.HttpUtil;
import com.eghm.websocket.account.service.HttpClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/11/27
 */
@Service("httpClientService")
@Slf4j
public class HttpClientServiceImpl implements HttpClientService {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String doPost(String url, Object request) {
        try {
            String requestBody = objectMapper.writeValueAsString(request);
            return HttpUtil.post(url, requestBody);
        } catch (JsonProcessingException e) {
            log.error("参数解析异常", e);
            throw new RuntimeException("参数解析异常");
        }
    }

}
