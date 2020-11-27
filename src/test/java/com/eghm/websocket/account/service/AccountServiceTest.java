package com.eghm.websocket.account.service;

import com.eghm.websocket.BaseTest;
import com.eghm.websocket.account.enums.CallType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountServiceTest extends BaseTest {

    @Autowired
    private AccountService accountService;


    @Test
    public void getCall() {
        accountService.getAwakeUrl(CallType.REGISTER, "13136213695", null);
    }
}