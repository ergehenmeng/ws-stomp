package com.eghm.websocket.dto;

import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/11/16
 */
@Data
public class SubscribeDoc {

    /**
     * 当前用户id
     */
    private String userId;

    /**
     * 文档内容
     */
    private String content;
}
