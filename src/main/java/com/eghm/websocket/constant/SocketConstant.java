package com.eghm.websocket.constant;

/**
 * @author 殿小二
 * @date 2020/11/16
 */
public class SocketConstant {

    /**
     * 聊天室前缀 0:spaceId 1:documentId
     */
    public static final String CHAT_ROOM_PREFIX = "/chatRoom/{1}/{2}";


    /**
     * 文档编辑前缀 0:spaceId 1:documentId
     */
    public static final String DOCUMENT_PREFIX = "/document/{0}/{1}";

    /**
     * 存放socket中的sessionUser
     */
    public static final String SESSION_USER = "sessionUser";
}


