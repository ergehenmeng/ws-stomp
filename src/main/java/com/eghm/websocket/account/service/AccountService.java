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
     * @param loginNo  登录号(唯一)
     * @param dataContent 附加参数 部分类型的拉起可能需要
     * @return 生成信息
     */
    CallRequest getCall(CallType callType, String loginNo, String dataContent);
}
