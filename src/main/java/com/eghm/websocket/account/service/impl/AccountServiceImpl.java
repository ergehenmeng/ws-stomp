package com.eghm.websocket.account.service.impl;

import com.eghm.websocket.account.constant.SignConstant;
import com.eghm.websocket.account.enums.CallType;
import com.eghm.websocket.account.request.CallRequest;
import com.eghm.websocket.account.service.AccountService;
import com.eghm.websocket.account.service.HttpClientService;
import com.eghm.websocket.account.service.SignService;
import com.eghm.websocket.utils.BaoFuProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private BaoFuProperties baoFuProperties;

    @Autowired
    private SignService signService;

    @Autowired
    private HttpClientService httpClientService;

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
        return httpClientService.doPost(baoFuProperties.getAwakeUrl(), request);
    }



}
