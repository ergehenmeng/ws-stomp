package com.eghm.websocket.mapper;


import com.eghm.websocket.model.Document;
import com.eghm.websocket.model.DocumentFile;

import java.util.List;

public interface DocumentMapper {

    /**
     * 通过workspaceId获取工作空间下的所有文档
     *
     * @param
     * @return
     */
    List<Document> getDocumentByWorkspaceId(Document document);

    /**
     * 增加文档对象
     *
     * @param document
     * @return Integer 返回生成主键ID
     */
    Integer createDocument(Document document);

    /**
     * 通过主键删除文档
     *
     * @param id 主键
     */
    void deleteDocumentById(Integer id);

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

    /**
     * 通过主键获取文档页信息
     *
     * @param id
     * @return
     */
    DocumentFile getDocumentFileById(Integer id);
}
