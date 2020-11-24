package com.eghm.websocket.account.enums;

/**
 * 宝付唤起H5类型
 * @author 二哥很猛
 */
public enum CallType {

    /**
     * 忘记密码
     */
    PWDFORGET,

    /**
     * 修改密码
     */
    PWDMODIFY,

    /**
     * 绑定银行卡
     */
    CARDBIND,

    /**
     * 解绑银行卡
     */
    CARDUNBIND,

    /**
     * 充值
     */
    RECHARGE,

    /**
     * 提现
     */
    WITHDRAW,

    /**
     * 开户/注册
     */
    REGISTER,

    /**
     * 支付
     */
    PAYMENT,

    /**
     *  V2 页面版本钱包支付
     */
    V2PAYMENT,

    /**
     * 分账支付
     */
    PAYMENT_SPLIT,

    /**
     * B 端用户 H5 唤起忘记密码
     */
    BM_PWDMODIFY,

    /**
     *  B 端用户 H5 唤起支付
     */
    BM_PAYMENT,

    /**
     *  B 端用户 H5 唤起支付分账
     */
    BM_PAYMENT_SPLIT,
    ;
}
