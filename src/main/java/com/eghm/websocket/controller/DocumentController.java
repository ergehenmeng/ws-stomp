package com.eghm.websocket.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.eghm.websocket.constant.SocketConstant;
import com.eghm.websocket.dto.RespBody;
import com.eghm.websocket.dto.SendChat;
import com.eghm.websocket.dto.SocketBody;
import com.eghm.websocket.dto.SubscribeDoc;
import com.eghm.websocket.dto.request.SearchDocumentRequest;
import com.eghm.websocket.enums.ActionType;
import com.eghm.websocket.enums.ErrorCode;
import com.eghm.websocket.enums.FileType;
import com.eghm.websocket.model.Document;
import com.eghm.websocket.model.User;
import com.eghm.websocket.service.DocumentService;
import com.eghm.websocket.utils.LimitQueue;
import com.eghm.websocket.utils.ShiroUtil;
import com.eghm.websocket.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author 二哥很猛
 */
@Controller
@Slf4j
public class DocumentController {


    @Autowired
    private DocumentService documentService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 聊天记录缓存
     */
    private Map<Long, LimitQueue<SendChat>> cacheChat = new ConcurrentHashMap<>();


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
        Document document = documentService.getById(documentId);
        Long userId = ShiroUtil.getUserId();
        model.addAttribute("userId", StringUtil.encryptNumber(userId));
        model.addAttribute("editable", userId.equals(document.getId()));
        model.addAttribute("content", document.getContent());
        model.addAttribute("title", document.getDocName());
        return "document";
    }


    /**
     * 订阅文档接口
     */
    @SubscribeMapping("/document/{spaceId}/{documentId}")
    public void document(SimpMessageHeaderAccessor accessor, @DestinationVariable("spaceId") Long spaceId, @DestinationVariable("documentId") Long documentId) {
        log.info("document被订阅 spaceId:[{}] documentId:[{}]", spaceId, documentId);
    }

}
