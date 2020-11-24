package com.eghm.websocket.account.service;

import com.eghm.websocket.account.enums.CallType;
import com.eghm.websocket.account.request.CallRequest;

/**
 * @author 二哥很猛
 */
public interface AccountService {

    /**
     * 生成响应信息
     * @param callType 唤起类型
     * @param loginNo  登录号
     * @return
     */
    CallRequest getCall(CallType callType, String loginNo);
}
