package com.eghm.websocket.account.service;

import com.eghm.websocket.BaseTest;
import com.eghm.websocket.account.enums.CallType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountServiceTest extends BaseTest {

    @Autowired
    private AccountService accountService;


    @Test
    public void getAwakeUrl() {
        System.out.println(accountService.getAwakeUrl(CallType.REGISTER, "13136213695", null));
    }

    @Test
    public void getEnterpriseOpenUrl() {
        accountService.enterpriseAccountApply("123123123", "664956140@qq.com");
    }
}