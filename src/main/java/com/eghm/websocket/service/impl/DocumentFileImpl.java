package com.eghm.websocket.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eghm.websocket.mapper.DocumentFileDao;
import com.eghm.websocket.model.DocumentFile;
import com.eghm.websocket.service.DocumentFileService;

@Service("documentFile")
public class DocumentFileImpl implements DocumentFileService{
	
	@Resource
	private DocumentFileDao documentFileDao;
	
	@Override
	public DocumentFile getDocumentFileById(Integer id) {
		
		return documentFileDao.getDocumentFileById(id);
	}

	@Override
	public void updateDocumentFile(DocumentFile documentFile) {
		documentFileDao.updateDocumentFile(documentFile);
	}

}
