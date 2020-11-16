package com.eghm.websocket.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.eghm.websocket.constant.SocketConstant;
import com.eghm.websocket.dto.ChatMessage;
import com.eghm.websocket.dto.SendChat;
import com.eghm.websocket.dto.SocketBody;
import com.eghm.websocket.enums.ActionType;
import com.eghm.websocket.enums.MsgType;
import com.eghm.websocket.model.User;
import com.eghm.websocket.utils.KeyGenerator;
import com.eghm.websocket.utils.LimitQueue;
import com.eghm.websocket.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 殿小二
 * @date 2020/11/16
 */
@Slf4j
@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private KeyGenerator keyGenerator;

    /**
     * 聊天记录缓存
     */
    private final Map<Long, LimitQueue<ChatMessage>> cacheChat = new ConcurrentHashMap<>();


    /**
     * 默认被调用一次
     * 初始化某个文档
     */
    @SubscribeMapping("/chatRoom/{spaceId}/{documentId}")
    public SocketBody<LimitQueue<ChatMessage>> chatRoom(@DestinationVariable("spaceId") Long spaceId, @DestinationVariable("documentId") Long documentId) {
        log.info("chatRoom被订阅 spaceId:[{}] documentId:[{}]", spaceId, documentId);
        //空间缓存不存在,则创建
        if (!cacheChat.containsKey(documentId)) {
            cacheChat.put(documentId, new LimitQueue<>(100));
        }
        LimitQueue<ChatMessage> userChats = cacheChat.get(documentId);
        return SocketBody.success(ActionType.SUBSCRIBE_CHAT, userChats);
    }

    /**
     * 接收聊天消息并转发聊天室
     *
     * @param accessor 获取用户sessionId等信息
     * @param sendChat 接收和要转发的信息
     */
    @MessageMapping("/sendGroupMsg")
    public void sendGroupMsg(SimpMessageHeaderAccessor accessor, @Payload SendChat sendChat) {
        Map<String, Object> map = accessor.getSessionAttributes();
        if (CollUtil.isNotEmpty(map)) {
            ChatMessage message = new ChatMessage();
            message.setId(keyGenerator.generateKey().longValue());
            message.setContent(sendChat.getContent());
            User user = (User) map.get(SocketConstant.SOCKET_USER);
            message.setUserId(StringUtil.encryptNumber(user.getId()));
            try {
                message.setContent(HtmlUtils.htmlEscape(URLDecoder.decode(sendChat.getContent(), "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                log.warn("URL解析失败 [{}]", sendChat.getContent(), e);
            }
            message.setNickName(user.getNickName());
            message.setCreateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            message.setMsgType(MsgType.TEXT);
            LimitQueue<ChatMessage> limit = cacheChat.get(sendChat.getDocumentId());
            limit.offer(message);
            // 向所有订阅该接口的用户发送聊天信息
            messagingTemplate.convertAndSend(MessageFormat.format(SocketConstant.CHAT_ROOM_PREFIX, sendChat.getSpaceId(), sendChat.getDocumentId()), SocketBody.success(ActionType.CHAT_MSG, message));
        }
    }
}
