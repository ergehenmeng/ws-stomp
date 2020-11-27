package com.eghm.websocket.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.eghm.websocket.account.service.HttpClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/11/27
 */
@Service("httpClientService")
@Slf4j
public class HttpClientServiceImpl implements HttpClientService {


    @Override
    public String doPost(String url, Object request) {
        log.info("远程调用请求参数:[{}]", JSONObject.toJSONString(request));
        String post = HttpUtil.post(url, BeanUtil.beanToMap(request));
        log.info("远程调用响应结果:[{}]", post);
        return post;
    }

}
