package com.eghm.websocket.service;

import java.util.List;

import com.eghm.websocket.enums.FileType;
import com.eghm.websocket.model.Document;

public interface DocumentService {

    /**
     * 通过workspaceId获取工作空间下的所有文档
     * @param spaceId   工作空间
     * @param orderColumn 排序字段
     * @param orderType 排序方式
     * @return 文档
     */
    List<Document> getBySpaceId(Long spaceId, String orderColumn, String orderType);

    /**
     * 创建文档对象
     */
    Document createDocument(Long spaceId, String docName, FileType type);

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
     * 更新文档信息
     */
    void updateSelective(Document document);

    /**
     * 主键查询
     * @param docId 主键
     * @return doc
     */
    Document getById(Long docId);


}
