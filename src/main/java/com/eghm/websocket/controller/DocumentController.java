package com.eghm.websocket.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.eghm.websocket.dto.RespBody;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


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
    private Map<Integer, LimitQueue<UserChat>> cacheChat = new ConcurrentHashMap<>();


    /**
     * 创建文档
     */
    @RequestMapping("/createDocument/{workspaceId}")
    @ResponseBody
    public RespBody<Object> createDocument(@PathVariable Integer workspaceId, String docName, FileType type) {
        documentService.createDocument(workspaceId, docName, type);
        return RespBody.success();
    }

    /**
     * 查询文档信息
     */
    @RequestMapping("/getDocument/{workspaceId}")
    @ResponseBody
    public RespBody<List<Document>> getDocument(@PathVariable Integer workspaceId) {
        List<Document> documentList = documentService.getByWorkspaceId(workspaceId);
        return RespBody.success(documentList);
    }

    /**
     * 删除文档
     */
    @RequestMapping("/deleteDocument/{workspaceId}")
    @ResponseBody
    public RespBody<Object> deleteDocument(@PathVariable String workspaceId, Integer docId) {
        documentService.deleteById(docId);
        return RespBody.success();
    }

    /**
     * 更新文档名称
     */
    @PostMapping("/updateDocument/{workspaceId}")
    @ResponseBody
    public RespBody<Object> updateDocument(@PathVariable String workspaceId, Integer docId, String docName) {
        Document document = documentService.getById(docId);
        document.setDocName(docName);
        documentService.updateSelective(document);
        return RespBody.success();
    }

    /**
     * 文档加密
     */
    @PostMapping("/createPassword/{workspaceId}")
    @ResponseBody
    public RespBody<Object> createPassword(@PathVariable Integer workspaceId, Integer docId, String pwd) {
        documentService.setPwd(docId, pwd);
        return RespBody.success();
    }

    /**
     * 密码验证
     */
    @RequestMapping("/checkPassword/{workspaceId}")
    @ResponseBody
    public RespBody<Object> checkPassword(@PathVariable Integer workspaceId, Integer docId, String pwd) {
        Document doc = documentService.getById(docId);
        if (StrUtil.isNotEmpty(doc.getPwd()) && !doc.getPwd().equals(pwd)) {
            return RespBody.error(ErrorCode.DOC_PWD_ERROR);
        }
        return RespBody.success();
    }

    /**
     * 文档管理页面
     */
    @RequestMapping("/document/{workspaceId}/{documentId}")
    public String document(@PathVariable String workspaceId, @PathVariable Integer documentId, ModelMap model) {
        model.addAttribute("workspaceId", workspaceId);
        model.addAttribute("documentId", documentId);
        return "document";
    }


    /**
     * 默认被调用一次
     * 初始化某个文档
     */
    @SubscribeMapping("/initDocument/{workspaceId}/{documentId}")
    public Map<String, Object> initDocument(SimpMessageHeaderAccessor accessor, @DestinationVariable Integer workspaceId, @DestinationVariable Integer documentId) {
        log.debug("文档空间: 工作空间ID: " + workspaceId + " 文档ID " + documentId);
        Document document = new Document();
        document.setId(documentId);
        document = documentService.getDocumentById(document);
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
            messagingTemplate.convertAndSend(DEST_DOCUMENT_URL + userChat.getWorkspaceId() + "/" + userChat.getDocumentId(), userChat);
            LimitQueue<UserChat> limit = cacheChat.get(userChat.getDocumentId());
            limit.offer(userChat);
        }
    }

    /**
     * 切换页
     */
    @RequestMapping("/changePage")
    @ResponseBody
    public RespBody<Page> changePage(Integer id) {
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
            messagingTemplate.convertAndSend(DEST_DOCUMENT_URL + page.getWorkspaceId() + "/" + page.getDocumentId(), result);
            pageService.updatePage(page.getId(), page.getContent());
        }
    }


}
