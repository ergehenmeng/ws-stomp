package com.eghm.websocket.account.response;

import lombok.Data;

/**
 * 针对普通接口响应的基类
 * @author 殿小二
 * @date 2020/11/27
 */
@Data
public class BaseResponse {

    /**
     * 成功或失败状态
     */
    private Boolean success;

    /**
     * 结果状态 没卵用
     */
    private Boolean result;

    /**
     * 错误码 success=false时有值
     */
    private String errorCode;

    /**
     * 错误信息 success=false时有值
     */
    private String errorMsg;
}
