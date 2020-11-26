package com.eghm.websocket.account.service.impl;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.eghm.websocket.account.service.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Enumeration;

/**
 * @author 殿小二
 * @date 2020/11/26
 */
@Service("signService")
@Slf4j
public class SignServiceImpl implements SignService {

    /**
     * 私钥
     */
    @Value("classpath:crypt/bfkey_100026363@@200001510.pfx")
    private Resource privateKeyResource = new ClassPathResource("crypt/bfkey_100026363@@200001510.pfx");

    /**
     * 公钥
     */
    @Value("${application.publicKey}")
    private String publicKey = "MIICWTCCAcKgAwIBAgIGAWmfKY3wMA0GCSqGSIb3DQEBDQUAMHAxHTAbBgNVBAMMFDEwMDAyNjM2M0BAMjAwMDAxNTEwMREwDwYDVQQHDAhzaGFuZ2hhaTERMA8GA1UECAwIU2hhbmdIYWkxCzAJBgNVBAYTAkNOMQswCQYDVQQKDAJiZjEPMA0GA1UECwwGYmFvZm9vMB4XDTE5MDMyMTA3MzEzNFoXDTI0MDMyMTA3MzEzNFowcDEdMBsGA1UEAwwUMTAwMDI2MzYzQEAyMDAwMDE1MTAxETAPBgNVBAcMCHNoYW5naGFpMREwDwYDVQQIDAhTaGFuZ0hhaTELMAkGA1UEBhMCQ04xCzAJBgNVBAoMAmJmMQ8wDQYDVQQLDAZiYW9mb28wgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMMn2BBMtx32FaobEw70JGK/2I+zaYhPeBWiX3VDs/y74FGjwf/B34OzEKb3TSuPxsO73vAryA/28rsfgepS3ZrDwbfmzAugg4Hs3IAe5UJI6VHKyM4Z+XoWp6wCshBOD+AuFQBoyzxXZlj48BY3u7eOYHE2Qx2emcEsbzQzpQkVAgMBAAEwDQYJKoZIhvcNAQENBQADgYEAk8gzLKa2Dyp4WJnMCmYowUfP0t7W2buzHEDRucuJ7igNXMayRq+gEdN9cKqHLo4bJWEIRYzxT4jHQeV91A6xPdqiA9y8O62M4M8SKjSsUAVnWts5i1WNozTFUc079eBPg3t/hPdmaN3yWN39Yte0FplZQnHAfS/nk3h3oP/ABWo=";

    /**
     * 秘钥
     */
    @Value("${application.password}")
    private String password = "100026363_899090";


    @Override
    public String encryptPrivateKey(String source) {
        RSA rsa = new RSA(this.getPrivateKey(), null);
        return rsa.encryptBase64(source, KeyType.PrivateKey);
    }

    @Override
    public String decryptPrivateKey(String source) {
        RSA rsa = new RSA(this.getPrivateKey(), null);
        return rsa.decryptStr(source, KeyType.PrivateKey);
    }

    @Override
    public String encryptPublicKey(String source) {
        RSA rsa = new RSA(null, this.getPublicKey());
        return rsa.encryptBase64(source, KeyType.PublicKey);
    }

    @Override
    public String decryptPublicKey(String source) {
        RSA rsa = new RSA(null, this.getPublicKey());
        return rsa.decryptStr(source, KeyType.PublicKey);
    }

    @Override
    public boolean verifySign(String source, String signature) {
        PublicKey publicKey = KeyUtil.generateRSAPublicKey(this.getPublicKey().getEncoded());
        Sign sign = new Sign(SignAlgorithm.SHA1withRSA, null, publicKey);
        return sign.verify(source.getBytes(), HexUtil.decodeHex(signature));
    }

    @Override
    public String sign(String source) {
        PrivateKey privateKey = KeyUtil.generateRSAPrivateKey(this.getPrivateKey().getEncoded());
        Sign sign = new Sign(SignAlgorithm.SHA1withRSA, privateKey, null);
        byte[] bytes = sign.sign(source.getBytes());
        return HexUtil.encodeHexStr(bytes);
    }

    private PrivateKey getPrivateKey() {
        try {
            char[] charPassword = password.toCharArray();
            KeyStore keyStore = KeyUtil.readPKCS12KeyStore(privateKeyResource.getInputStream(), charPassword);
            Enumeration<String> aliasEnum = keyStore.aliases();
            String keyAlias = null;
            if (aliasEnum.hasMoreElements()) {
                keyAlias = aliasEnum.nextElement();
            }
            return (PrivateKey) keyStore.getKey(keyAlias, charPassword);
        } catch (Exception e) {
            log.error("获取私钥失败", e);
            throw new RuntimeException("获取私钥失败");
        }
    }

    private PublicKey getPublicKey() {
        return SecureUtil.readCertificate("X509",new ByteArrayInputStream(Base64Decoder.decode(publicKey))).getPublicKey();
    }

}
