package com.eghm.websocket.service;

import java.util.List;

import com.eghm.websocket.model.Document;

public interface DocumentService {

    /**
     * 通过workspaceId获取工作空间下的所有文档
     *
     * @return
     */
    List<Document> getDocumentByWorkspaceId(Document document);

    /**
     * 创建文档对象
     *
     * @param document
     * @return
     */
    Document createDocument(Document document);

    /**
     * 通过主键删除文档
     *
     * @param document
     */
    void deleteDocumentById(Document document);

    /**
     * 更新文档信息
     *
     * @param document
     */
    void updateDocument(Document document);

    /**
     * 通过id,用户id查询文档信息
     *
     * @param document
     * @return
     */
    Document getDocumentById(Document document);


}
