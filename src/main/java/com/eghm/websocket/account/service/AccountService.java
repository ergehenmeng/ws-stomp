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
     * @return 跳转的地址
     */
    String getAwakeUrl(CallType callType, String loginNo, String dataContent);


    /**
     * 企业申请开户
     * @param loginNo 登录号(唯一)
     * @param email 邮箱号(用于注册接收验证等)
     */
    void enterpriseAccountApply(String loginNo, String email);
}
