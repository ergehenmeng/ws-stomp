package com.eghm.websocket.model;

import com.eghm.websocket.enums.MsgType;
import lombok.Data;

/**
 * 用户聊天记录
 * @author 二哥很猛
 */
@Data
public class UserChat {

    /**
     * 用户id
     */
    public Long id;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 聊天信息
     */
    private String content;

    /**
     * 文档空间ID
     */
    private Long documentId;

    /**
     * 消息发送时间
     */
    private String createTime;

    /**
     * 消息类型
     */
    private MsgType msgType;

    /**
     * 工作空间
     */
    private Long spaceId;

}
