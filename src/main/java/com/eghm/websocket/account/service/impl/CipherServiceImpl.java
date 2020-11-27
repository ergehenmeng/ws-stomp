package com.eghm.websocket.account.service.impl;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.eghm.websocket.account.service.CipherService;
import com.eghm.websocket.utils.BaoFuProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service("cipherService")
@Slf4j
public class CipherServiceImpl implements CipherService {

    @Autowired
    private BaoFuProperties baoFuProperties;


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
            char[] charPassword = baoFuProperties.getPassword().toCharArray();
            KeyStore keyStore = KeyUtil.readPKCS12KeyStore(baoFuProperties.getPrivateKey().getInputStream(), charPassword);
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
        return SecureUtil.readCertificate("X509",new ByteArrayInputStream(Base64Decoder.decode(baoFuProperties.getPublicKey()))).getPublicKey();
    }

}
