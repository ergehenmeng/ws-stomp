package com.eghm.websocket.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.eghm.websocket.dto.RespBody;
import com.eghm.websocket.dto.request.SearchDocumentRequest;
import com.eghm.websocket.enums.ErrorCode;
import com.eghm.websocket.enums.FileType;
import com.eghm.websocket.model.Document;
import com.eghm.websocket.model.Page;
import com.eghm.websocket.model.User;
import com.eghm.websocket.model.UserChat;
import com.eghm.websocket.service.DocumentService;
import com.eghm.websocket.service.PageService;
import com.eghm.websocket.utils.CommonConstant;
import com.eghm.websocket.utils.LimitQueue;
import com.eghm.websocket.utils.ShiroUtil;
import com.eghm.websocket.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author 二哥很猛
 */
@Controller
@Slf4j
public class DocumentController {

    private static final String DEST_DOCUMENT_URL = "/document/";

    @Autowired
    private DocumentService documentService;

    @Autowired
    private PageService pageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 聊天记录缓存
     */
    private Map<Long, LimitQueue<UserChat>> cacheChat = new ConcurrentHashMap<>();


    /**
     * 创建文档
     */
    @PostMapping("/createDocument")
    @ResponseBody
    public RespBody<Object> createDocument(Long spaceId, String docName, FileType fileType) {
        documentService.createDocument(spaceId, docName, fileType);
        return RespBody.success();
    }

    /**
     * 查询文档信息
     */
    @GetMapping("/searchDocument")
    @ResponseBody
    public RespBody<List<Document>> searchDocument(SearchDocumentRequest request) {
        List<Document> documentList = documentService.getList(request);
        return RespBody.success(documentList);
    }

    /**
     * 删除文档
     */
    @PostMapping("/deleteDocument")
    @ResponseBody
    public RespBody<Object> deleteDocument(Long docId) {
        documentService.deleteById(docId);
        return RespBody.success();
    }

    /**
     * 更新文档名称
     */
    @PostMapping("/updateDocument")
    @ResponseBody
    public RespBody<Object> updateDocument(Long docId, String docName) {
        documentService.updateDocName(docId, docName);
        return RespBody.success();
    }

    /**
     * 文档加密
     */
    @PostMapping("/createPassword")
    @ResponseBody
    public RespBody<Object> createPassword(Long docId, String pwd) {
        documentService.setPwd(docId, pwd);
        return RespBody.success();
    }

    /**
     * 密码验证
     */
    @RequestMapping("/checkPassword")
    @ResponseBody
    public RespBody<Object> checkPassword(Long spaceId, Long docId, String pwd) {
        Document doc = documentService.getById(docId);
        if (StrUtil.isNotEmpty(doc.getPwd()) && !doc.getPwd().equals(pwd)) {
            return RespBody.error(ErrorCode.DOC_PWD_ERROR);
        }
        return RespBody.success();
    }

    /**
     * 文档管理页面
     */
    @RequestMapping("/document/{spaceId}/{documentId}")
    public String document(@PathVariable Long spaceId, @PathVariable Long documentId, Model model) {
        model.addAttribute("spaceId", spaceId);
        model.addAttribute("documentId", documentId);
        model.addAttribute("userId", StringUtil.encryptNumber(ShiroUtil.getUserId()));
        return "document";
    }


    /**
     * 默认被调用一次
     * 初始化某个文档
     */
    @SubscribeMapping("/initDocument/{spaceId}/{documentId}")
    public Map<String, Object> initDocument(SimpMessageHeaderAccessor accessor, @DestinationVariable Long spaceId, @DestinationVariable Long documentId) {
        log.debug("文档空间: 工作空间ID: " + spaceId + " 文档ID " + documentId);
        Document document = documentService.getById(documentId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("document", document);
        //空间缓存不存在,则创建
        if (!cacheChat.containsKey(documentId)) {
            cacheChat.put(documentId, new LimitQueue<>(50));
        }
        result.put("chat", cacheChat.get(documentId));

        return result;
    }


    /**
     * 接收并转发聊天室的消息
     *
     * @param accessor 获取用户sessionId等信息
     * @param userChat 接收和要转发的信息
     */
    @MessageMapping("/userChat")
    public void sendMessage(SimpMessageHeaderAccessor accessor, UserChat userChat) {
        Map<String, Object> map = accessor.getSessionAttributes();
        User user = (User) map.get(CommonConstant.SESSION_USER);
        if (user != null) {
            userChat.setId(user.getId());
            try {
                userChat.setChatContent(HtmlUtils.htmlEscape(URLDecoder.decode(userChat.getChatContent(), "utf-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            userChat.setNickName(user.getNickName());
            userChat.setCreateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            userChat.setType(1);
            messagingTemplate.convertAndSend(DEST_DOCUMENT_URL + userChat.getSpaceId() + "/" + userChat.getDocumentId(), userChat);
            LimitQueue<UserChat> limit = cacheChat.get(userChat.getDocumentId());
            limit.offer(userChat);
        }
    }

    /**
     * 切换页
     */
    @RequestMapping("/changePage")
    @ResponseBody
    public RespBody<Page> changePage(Long id) {
        Page page = pageService.getById(id);
        return RespBody.success(page);
    }

    /**
     * 文档内容改变后同步
     */
    @MessageMapping("/updatePage")
    public void updatePage(SimpMessageHeaderAccessor accessor, Page page) {
        Map<String, Object> map = accessor.getSessionAttributes();
        User user = (User) map.get(CommonConstant.SESSION_USER);
        Map<String, Object> result = new HashMap<String, Object>();
        if (user != null) {
            result.put("id", page.getId());
            result.put("userId", user.getId());
            result.put("content", page.getContent());
            result.put("type", 2);
            messagingTemplate.convertAndSend(DEST_DOCUMENT_URL + page.getSpaceId() + "/" + page.getDocumentId(), result);
            pageService.updatePage(page.getId(), page.getContent());
        }
    }


}
