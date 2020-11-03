package com.eghm.websocket.service;

import java.util.List;

import com.eghm.websocket.enums.FileType;
import com.eghm.websocket.model.Document;

public interface DocumentService {

    /**
     * 通过workspaceId获取工作空间下的所有文档
     */
    List<Document> getByWorkspaceId(Integer workspaceId);

    /**
     * 创建文档对象
     */
    Document createDocument(Integer workspaceId, String docName, FileType type);

    /**
     * 通过主键删除文档 逻辑删除
     */
    void deleteById(Integer id);

    /**
     * 设置文档密码
     * @param docId docId
     * @param pwd pwd
     */
    void setPwd(Integer docId, String pwd);

    /**
     * 更新文档信息
     */
    void updateSelective(Document document);

    /**
     * 主键查询
     * @param docId 主键
     * @return doc
     */
    Document getById(Integer docId);


}
