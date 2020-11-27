package com.eghm.websocket.account.service;

import com.eghm.websocket.BaseTest;
import com.eghm.websocket.account.enums.CallType;
import com.eghm.websocket.account.request.CallRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class AccountServiceTest extends BaseTest {

    @Autowired
    private AccountService accountService;


    @Test
    public void getCall() {
        CallRequest request = accountService.getCall(CallType.REGISTER, "13136113695", null);
        System.out.println(request);
    }
}