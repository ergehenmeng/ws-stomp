package com.eghm.websocket.dto;

import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/11/16
 */
@Data
public class SyncDoc {

    /**
     * 文档作者id
     */
    private String author;

    /**
     * 文档内容
     */
    private String content;

}
