package com.eghm.websocket.account.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
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

    /**
     * 公钥
     */
    @Value("${application.publicKey:'MIICWTCCAcKgAwIBAgIGAWVl3luyMA0GCSqGSIb3DQEBDQUAMHAxHTAbBgNVBAMMFDEwMDAyNjEzNkBAMjAwMDAxMjU5MREwDwYDVQQHDAhzaGFuZ2hhaTERMA8GA1UECAwIU2hhbmdIYWkxCzAJBgNVBAYTAkNOMQswCQYDVQQKDAJiZjEPMA0GA1UECwwGYmFvZm9vMB4XDTE4MDgyMzA4MTk1NVoXDTIwMDgyNzA4MTk1NVowcDEdMBsGA1UEAwwUMTAwMDI2MTM2QEAyMDAwMDEyNTkxETAPBgNVBAcMCHNoYW5naGFpMREwDwYDVQQIDAhTaGFuZ0hhaTELMAkGA1UEBhMCQ04xCzAJBgNVBAoMAmJmMQ8wDQYDVQQLDAZiYW9mb28wgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAJ+ct7xpZBsmefM9cizGMz03MdGeI0/4GpVXMs3Vbt3vExVIkTHvNcaXZNldMKcaXYQZcWCJwKHNOsPF/yU9KmT3FZ23dEMLLgX3M1+pp9yQuH5kEQkXQvGgtT6EVO6yj8AdUa3BZYzjkTLdg9x/mpS1zyRx6rl5xqyOhcYhwfwTAgMBAAEwDQYJKoZIhvcNAQENBQADgYEAdOE5AFObRp2W1GxgvJZQi/4jYOhOb5LivLdye/bfP74vl8fkCXpyFO7Re4m8Tzqmwa7xzpNvP1IVYFgzUbFiPlGgSEZ31saP9jd7+CrKS02hq2U209TF7MU2KA0QhpuWtppfttzWJOTY/Hn0OxXr2E1wYFthTpVovJX3/3wV0Bg='}")
    private String publicKey;

    @Value("${application.privateKey:''}")
    private String privateKey;

    @Override
    public CallRequest getCall(CallType callType, String loginNo) {
        CallRequest request = new CallRequest();
        request.setOrgNo(orgNo);
        request.setMerchantNo(merchantNo);
        request.setTerminalNo(terminalNo);
        request.setCallType(callType);
        request.setLoginNo(loginNo);
        request.setSignData("");

        return null;
    }

    private String signRequest(CallRequest request) {
        RSA rsa = new RSA(null, publicKey);
        String base64 = rsa.encryptBase64("ABC".getBytes(), KeyType.PublicKey);
        System.out.println(base64);
        return base64;
    }

    public static void main(String[] args) {
        byte[] readBytes = FileUtil.readBytes("D:\\BusinessBaoHuTong\\BusinessBaoHuTong\\WebContent\\CER\\bfkey_100026363@@200001510.pfx");

        RSA rsa = new RSA(readBytes, null);
        String base64 = rsa.encryptBase64("ABC".getBytes(), KeyType.PrivateKey);

        System.out.println(base64);
    }
}
