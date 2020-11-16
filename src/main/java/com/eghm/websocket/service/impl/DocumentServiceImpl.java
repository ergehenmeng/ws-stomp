package com.eghm.websocket.service.impl;

import com.eghm.websocket.dto.request.SearchDocumentRequest;
import com.eghm.websocket.enums.ErrorCode;
import com.eghm.websocket.enums.FileType;
import com.eghm.websocket.exception.SystemException;
import com.eghm.websocket.mapper.DocumentMapper;
import com.eghm.websocket.model.Document;
import com.eghm.websocket.service.DocumentService;
import com.eghm.websocket.utils.KeyGenerator;
import com.eghm.websocket.utils.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentMapper documentMapper;

    @Autowired
    private KeyGenerator keyGenerator;

    @Override
    public List<Document> getList(SearchDocumentRequest request) {
        return documentMapper.getList(request);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createDocument(Long spaceId, String docName, FileType type) {
        Document document = new Document();
        document.setId(keyGenerator.generateKey().longValue());
        document.setDocName(docName);
        document.setSpaceId(spaceId);
        document.setUserId(ShiroUtil.getUserId());
        document.setType(type.getType());
        documentMapper.insertSelective(document);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteById(Long id) {
        Document document = this.getModifyDocument(id);
        document.setState((byte) 0);
        documentMapper.updateByPrimaryKeySelective(document);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void setPwd(Long docId, String pwd) {
        Document document = this.getModifyDocument(docId);
        document.setPwd(pwd);
        documentMapper.updateByPrimaryKeySelective(document);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateSelective(Document document) {
        documentMapper.updateByPrimaryKeySelective(document);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateDocName(Long docId, String docName) {
        Document document = this.getModifyDocument(docId);
        document.setDocName(docName);
        documentMapper.updateByPrimaryKeySelective(document);
    }


    private Document getModifyDocument(Long docId) {
        Document document = documentMapper.selectByPrimaryKey(docId);
        Long userId = ShiroUtil.getUserId();
        if (!userId.equals(document.getUserId())) {
            throw new SystemException(ErrorCode.ILLEGAL_MODIFY);
        }
        return document;
    }

    @Override
    public Document getById(Long docId) {
        return documentMapper.selectByPrimaryKey(docId);
    }
}
