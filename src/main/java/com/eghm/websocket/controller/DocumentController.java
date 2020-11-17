package com.eghm.websocket.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.eghm.websocket.constant.SocketConstant;
import com.eghm.websocket.dto.RespBody;
import com.eghm.websocket.dto.SendDoc;
import com.eghm.websocket.dto.SocketBody;
import com.eghm.websocket.dto.SyncDoc;
import com.eghm.websocket.dto.request.SearchDocumentRequest;
import com.eghm.websocket.enums.ActionType;
import com.eghm.websocket.enums.ErrorCode;
import com.eghm.websocket.enums.FileType;
import com.eghm.websocket.model.Document;
import com.eghm.websocket.model.User;
import com.eghm.websocket.service.DocumentService;
import com.eghm.websocket.service.DocumentTask;
import com.eghm.websocket.utils.ShiroUtil;
import com.eghm.websocket.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


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

    @Autowired
    private DocumentTask documentTask;


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
        request.setUserId(ShiroUtil.getUserId());
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
    public RespBody<Object> checkPassword(Long docId, String pwd) {
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
        // wangEditor不吃转义后的html 可能出现xss???
        model.addAttribute("content", StringEscapeUtils.unescapeHtml4(document.getContent()));
        model.addAttribute("title", document.getDocName());
        return "document";
    }


    /**
     * 订阅文档接口
     */
    @SubscribeMapping("/document/{spaceId}/{documentId}")
    public void document(@DestinationVariable("spaceId") Long spaceId, @DestinationVariable("documentId") Long documentId) {
        log.info("document被订阅 spaceId:[{}] documentId:[{}]", spaceId, documentId);
    }

    /**
     * 同步文档信息
     */
    @MessageMapping("/syncDocument")
    public void syncDocument(SimpMessageHeaderAccessor accessor, @Payload SendDoc doc) throws ExecutionException {
        Map<String, Object> attributes = accessor.getSessionAttributes();
        if (CollUtil.isNotEmpty(attributes)) {
            User user = (User) attributes.get(SocketConstant.SOCKET_USER);
            Document document = documentService.getCacheById(doc.getDocumentId());
            // 将文档通给所有人 不包含作者本人(因为该动作就是他自己触发的)
            if (document.getUserId().equals(user.getId())) {
                // 转义一波防止xss注入
                doc.setContent(StringEscapeUtils.escapeHtml4(doc.getContent()));
                documentTask.syncContent(doc);
                SyncDoc syncDoc = new SyncDoc();
                syncDoc.setContent(doc.getContent());
                syncDoc.setAuthor(StringUtil.encryptNumber(user.getId()));
                messagingTemplate.convertAndSend(MessageFormat.format(SocketConstant.DOCUMENT_PREFIX, doc.getSpaceId(), doc.getDocumentId()), SocketBody.success(ActionType.SYNC_CONTENT, syncDoc));
                return;
            }
            log.warn("非创建人无法操作该文档 author: [{}] userId:[{}}", document.getUserId(), user.getId());
        }
    }
}
