package com.eghm.websocket.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eghm.websocket.mapper.DocumentMapper;
import com.eghm.websocket.model.Document;
import com.eghm.websocket.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService{
	
	@Resource
	private DocumentMapper documentMapper;
	


	@Override
	public List<Document> getDocumentByWorkspaceId(Document document) {
		
		return documentMapper.getDocumentByWorkspaceId(document);
	}

	@Override
	public Document createDocument(Document docEntity) {
		Date nowDate = new Date();

		Integer id = documentMapper.createDocument(docEntity);
		docEntity.setId(id);
		return docEntity;
	}


	@Override
	public void deleteDocumentById(Document document) {
		if(document.getId() == null) return;
		document.setState(1);
		documentMapper.updateDocument(document);
	}


	@Override
	public void updateDocument(Document document) {
		documentMapper.updateDocument(document);
	}


	@Override
	public Document getDocumentById(Document document) {
		
		return documentMapper.getDocumentById(document);
	}


	



	
	
	

}
