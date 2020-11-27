package com.eghm.websocket.account.service.impl;

import com.eghm.websocket.BaseTest;
import com.eghm.websocket.account.enums.BusinessType;
import com.eghm.websocket.account.request.PersonOpenAccountRequest;
import com.eghm.websocket.account.service.SignService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class SignServiceImplTest extends BaseTest {

    @Autowired
    private SignService signService;

    @Test
    public void sign() {
        PersonOpenAccountRequest request = new PersonOpenAccountRequest();
        request.setLoginNo("ABC");
        request.setCertificateNo("ABC");
        request.setCustomerName("ABC");
        request.setTerminalNo("ABC");
        request.setOrgNo("ABC");
        System.out.println(signService.sign(BusinessType.PERSON_OPEN_ACCOUNT, request));
    }
}