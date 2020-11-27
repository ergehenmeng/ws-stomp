package com.eghm.websocket.account.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 殿小二
 * @date 2020/11/27
 */
@Getter
@Setter
public class AwakeResponse extends BaseResponse{

    /**
     * 成功后跳转的地址
     */
    private String redirectUrl;
}
