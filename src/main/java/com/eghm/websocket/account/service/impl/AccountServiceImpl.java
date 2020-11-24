package com.eghm.websocket.account.service.impl;

import com.eghm.websocket.account.enums.CallType;
import com.eghm.websocket.account.request.CallRequest;
import com.eghm.websocket.account.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Value("application.orgNo:''")
    private String orgNo;

    @Value("${application.merchantNo:''}")
    private String merchantNo;

    @Value("${application.terminalNo:''}")
    private String terminalNo;

    @Override
    public CallRequest getCall(CallType callType, String loginNo) {
        CallRequest request = new CallRequest();
        request.setOrgNo(orgNo);
        request.setMerchantNo(merchantNo);
        request.setTerminalNo(terminalNo);
        request.setCallType(callType);
        request.setLoginNo(loginNo);
        request.setSignData();

        return null;
    }
}
