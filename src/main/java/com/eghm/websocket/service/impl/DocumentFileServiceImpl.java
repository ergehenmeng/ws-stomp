package com.eghm.websocket.service.impl;

import com.eghm.websocket.mapper.DocumentFileMapper;
import com.eghm.websocket.model.DocumentFile;
import com.eghm.websocket.service.DocumentFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DocumentFileServiceImpl implements DocumentFileService {

    @Resource
    private DocumentFileMapper documentFileMapper;

    @Override
    public DocumentFile getDocumentFileById(Integer id) {
        return documentFileMapper.getDocumentFileById(id);
    }

    @Override
    public void updateDocumentFile(DocumentFile documentFile) {
        documentFileMapper.updateDocumentFile(documentFile);
    }

}
