package com.eghm.websocket.account.service;

/**
 * @author 殿小二
 * @date 2020/11/26
 */
public interface SignService {

    /**
     * 使用私钥加密签名
     * @param source 原字符串
     * @return 签名后的字符串
     */
    String encryptPrivateKey(String source);

    /**
     * 使用私钥解密
     * @param source 加密后的字符串
     * @return 解密后的字符串
     */
    String decryptPrivateKey(String source);

    /**
     * 使用公钥加密签名
     * @param source 原字符串
     * @return 签名后的字符串
     */
    String encryptPublicKey(String source);

    /**
     * 使用公钥加密签名
     * @param source 原字符串
     * @return 签名后的字符串
     */
    String decryptPublicKey(String source);

    /**
     * 验签校验 使用公钥验签
     * @param source 原始字符串
     * @param signature 第三方加密的串
     * @return true:验签通过 false:验签失败
     */
    boolean verifySign(String source, String signature);

    /**
     * 签名
     * @param source 原始字符串
     * @return 签名后的字符串
     */
    String sign(String source);
}
