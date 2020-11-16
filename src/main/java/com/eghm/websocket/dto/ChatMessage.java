package com.eghm.websocket.dto;

import com.eghm.websocket.enums.MsgType;
import lombok.Data;

/**
 * 用于存放用户聊天的信息
 * @author 殿小二
 * @date 2020/11/16
 */
@Data
public class ChatMessage {

    /**
     * 消息id 唯一
     */
    private Long id;

    /**
     * 消息的发送者 用户id(加密)
     */
    public String userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 聊天信息
     */
    private String content;

    /**
     * 消息发送时间
     */
    private String createTime;

    /**
     * 消息类型
     */
    private MsgType msgType;

}
