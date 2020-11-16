package com.eghm.websocket.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户发送聊天信息
 * @author 二哥很猛
 */
@Data
public class SendChat implements Serializable {

    /**
     * 命名空间
     */
    public Long spaceId;

    /**
     * 文档空间ID
     */
    private Long documentId;

    /**
     * 聊天信息
     */
    private String content;

}
