package com.eghm.websocket.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.eghm.websocket.constant.SocketConstant;
import com.eghm.websocket.dto.SocketBody;
import com.eghm.websocket.enums.ActionType;
import com.eghm.websocket.enums.MsgType;
import com.eghm.websocket.model.User;
import com.eghm.websocket.model.UserChat;
import com.eghm.websocket.utils.CommonConstant;
import com.eghm.websocket.utils.LimitQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
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

    /**
     * 聊天记录缓存
     */
    private final Map<Long, LimitQueue<UserChat>> cacheChat = new ConcurrentHashMap<>();


    /**
     * 默认被调用一次
     * 初始化某个文档
     */
    @SubscribeMapping("/chatRoom/{spaceId}/{documentId}")
    public SocketBody<LimitQueue<UserChat>> initDocument(Long spaceId, Long documentId) {
        log.info("chatRoom被订阅:[{}]-[{}]", spaceId, documentId);
        //空间缓存不存在,则创建
        if (!cacheChat.containsKey(documentId)) {
            cacheChat.put(documentId, new LimitQueue<>(100));
        }
        LimitQueue<UserChat> userChats = cacheChat.get(documentId);
        return SocketBody.success(ActionType.SUBSCRIBE_CHAT,userChats);
    }

    /**
     * 接收聊天消息并转发聊天室
     *
     * @param accessor 获取用户sessionId等信息
     * @param userChat 接收和要转发的信息
     */
    @MessageMapping("/sendGroupMsg")
    public void sendGroupMsg(SimpMessageHeaderAccessor accessor, UserChat userChat) {
        Map<String, Object> map = accessor.getSessionAttributes();
        if (CollUtil.isNotEmpty(map)) {
            User user = (User) map.get(CommonConstant.SESSION_USER);
            userChat.setId(user.getId());
            try {
                userChat.setContent(HtmlUtils.htmlEscape(URLDecoder.decode(userChat.getContent(), "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            userChat.setNickName(user.getNickName());
            userChat.setCreateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            userChat.setMsgType(MsgType.TEXT);
            LimitQueue<UserChat> limit = cacheChat.get(userChat.getDocumentId());
            limit.offer(userChat);
            // 向所有订阅该接口的用户发送聊天信息
            messagingTemplate.convertAndSend(MessageFormat.format(SocketConstant.CHAT_ROOM_PREFIX, userChat.getSpaceId(), userChat.getDocumentId()), SocketBody.success(ActionType.CHAT_MSG, userChat));
        }
    }
}
