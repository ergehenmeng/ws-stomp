package com.eghm.websocket.service;

import com.eghm.websocket.dto.request.SearchDocumentRequest;
import com.eghm.websocket.enums.FileType;
import com.eghm.websocket.model.Document;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface DocumentService {

    /**
     * 通过spaceId获取工作空间下的所有文档
     * @param request 查询条件
     * @return 文档
     */
    List<Document> getList(SearchDocumentRequest request);

    /**
     * 创建文档对象
     * @param spaceId 文档所属工作空间
     * @param docName 文档名
     * @param type 文档类型
     */
    void createDocument(Long spaceId, String docName, FileType type);

    /**
     * 通过主键删除文档 逻辑删除
     */
    void deleteById(Long id);

    /**
     * 设置文档密码
     * @param docId docId
     * @param pwd pwd
     */
    void setPwd(Long docId, String pwd);

    /**
     * 不为空更新文档信息
     * @param docId 文档id
     * @param content 文档内容
     */
    void updateContent(Long docId, String content);

    /**
     * 更新文档名称
     * @param docId docId
     * @param docName docName
     */
    void updateDocName(Long docId, String docName);

    /**
     * 主键查询
     * @param docId 主键
     * @return doc
     * @throws ExecutionException 缓存异常
     */
    Document getCacheById(Long docId) throws ExecutionException;

    /**
     * 主键查询
     * @param docId 主键
     * @return doc
     */
    Document getById(Long docId);
}
