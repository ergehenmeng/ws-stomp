package com.eghm.websocket.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author 殿小二
 * @date 2020/11/16
 */
@Data
public class SendDoc {

    /**
     * 发送的内容
     */
    private String content;

    /**
     * 文档id
     */
    private Long documentId;

    /**
     * 工作空间id
     */
    private Long spaceId;
}
