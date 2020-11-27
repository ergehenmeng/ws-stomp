package com.eghm.websocket.account.service.impl;

import com.eghm.websocket.account.constant.SignConstant;
import com.eghm.websocket.account.enums.CallType;
import com.eghm.websocket.account.request.CallRequest;
import com.eghm.websocket.account.request.EnterpriseOpenAccountRequest;
import com.eghm.websocket.account.response.AwakeResponse;
import com.eghm.websocket.account.response.BaseResponse;
import com.eghm.websocket.account.response.H5Response;
import com.eghm.websocket.account.service.AccountService;
import com.eghm.websocket.account.service.HttpClientService;
import com.eghm.websocket.account.service.SignService;
import com.eghm.websocket.utils.BaoFuProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2020/11/25
 */
@Service("accountService")
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private BaoFuProperties baoFuProperties;

    @Autowired
    private SignService signService;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String getAwakeUrl(CallType callType, String loginNo, String dataContent) {
        CallRequest request = new CallRequest();
        request.setOrgNo(baoFuProperties.getOrgNo());
        request.setMerchantNo(baoFuProperties.getMerchantNo());
        request.setTerminalNo(baoFuProperties.getTerminalNo());
        request.setCallType(callType);
        request.setLoginNo(loginNo);
        request.setDataContent(dataContent);
        String signData = signService.sign(SignConstant.AWAKE_SIGN, request);
        request.setSignData(signData);
        String responseBody = httpClientService.doPost(baoFuProperties.getAwakeUrl(), request);
        return this.parseH5Response(responseBody, AwakeResponse.class).getRedirectUrl();
    }

    @Override
    public void enterpriseAccountApply(String loginNo, String email) {
        EnterpriseOpenAccountRequest request = new EnterpriseOpenAccountRequest();
        request.setOrgNo(baoFuProperties.getOrgNo());
        request.setTerminalNo(baoFuProperties.getTerminalNo());
        request.setLoginNo(loginNo);
        request.setEmail(email);
        request.setNotifyUrl(baoFuProperties.getEnterpriseNotifyUrl());
        String signData = signService.sign(SignConstant.ENTERPRISE_SIGN, request);
        request.setSignData(signData);
        String response = httpClientService.doPost(baoFuProperties.getEnterpriseOpenUrl(), request);
        this.parseResponse(response, BaseResponse.class);
    }


    /**
     * 解析后台接口响应
     * @param response response
     * @param cls cls
     * @param <T> 类型
     * @return response
     */
    private <T extends BaseResponse> T parseResponse(String response, Class<T> cls) {
        T responseValue;
        try {
            responseValue = objectMapper.readValue(response, cls);
        } catch (Exception e) {
            log.error("响应参数解析异常 [{}] [{}]", response, cls, e);
            throw new RuntimeException("参数解析异常");
        }
        if (Boolean.TRUE.equals(responseValue.getSuccess())) {
            return responseValue;
        }
        log.error("宝付返回结果错误 [{}] [{}]", responseValue.getErrorCode(), responseValue.getErrorMsg());
        throw new RuntimeException(responseValue.getErrorMsg());
    }


    /**
     * 解析H5响应
     * @param response response
     * @param cls cls
     * @param <T> 类型
     * @return response
     */
    private <T extends H5Response> T parseH5Response(String response, Class<T>  cls) {
        T responseValue;
        try {
            responseValue = objectMapper.readValue(response, cls);
        } catch (Exception e) {
            log.error("响应参数解析异常 [{}] [{}]", response, cls, e);
            throw new RuntimeException("参数解析异常");
        }
        if (responseValue.getRetCode().equals(H5Response.SUCCESS)) {
            return responseValue;
        }
        log.error("宝付返回结果错误 [{}]", responseValue.getRetMsg());
        throw new RuntimeException("银行响应信息错误");
    }
}
