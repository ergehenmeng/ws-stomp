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
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author 二哥很猛
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentMapper documentMapper;

    @Autowired
    private KeyGenerator keyGenerator;

    private LoadingCache<Long, Document> loadingCache = CacheBuilder.newBuilder()
            .concurrencyLevel(8)
            .maximumSize(100)
            .expireAfterWrite(600, TimeUnit.SECONDS)
            .build(new CacheLoader<Long, Document>() {
                @Override
                public Document load(@NonNull Long key) {
                    return documentMapper.selectByPrimaryKey(key);
                }
            });


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
    public void updateContent(Long docId, String content) {
        Document document = new Document();
        document.setId(docId);
        document.setContent(content);
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
    public Document getCacheById(Long docId) {
        return loadingCache.getIfPresent(docId);
    }

    @Override
    public Document getById(Long docId) {
        return documentMapper.selectByPrimaryKey(docId);
    }
}
