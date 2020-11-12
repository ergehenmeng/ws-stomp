package com.eghm.websocket.mapper;


import com.eghm.websocket.model.DocumentFile;

public interface DocumentFileMapper {

    /**
     * 通过主键获取文档页信息
     *
     * @param id
     * @return
     */
    DocumentFile getDocumentFileById(Integer id);

    /**
     * 对象更新 通过ID
     *
     * @param documentFile
     */
    void updateDocumentFile(DocumentFile documentFile);
}
