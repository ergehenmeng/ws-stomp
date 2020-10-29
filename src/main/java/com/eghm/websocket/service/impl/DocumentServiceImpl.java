package com.eghm.websocket.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eghm.websocket.mapper.CoordinationMapper;
import com.eghm.websocket.mapper.DocumentDao;
import com.eghm.websocket.model.Document;
import com.eghm.websocket.service.DocumentService;

@Service("documentService")
public class DocumentServiceImpl implements DocumentService{
	
	@Resource
	private DocumentDao documentDao;
	
	@Resource
	private CoordinationMapper coordinationMapper;
	

	@Override
	public List<Document> getDocumentByWorkspaceId(Document document) {
		
		return documentDao.getDocumentByWorkspaceId(document);
	}
	
	
	@Override
	public Document createDocument(Document docEntity) {
		Date nowDate = new Date();
		docEntity.setCreateDate(nowDate);
		docEntity.setUpdateDate(nowDate);
		docEntity.setState(0);
		docEntity.setIsShow(0);
		Integer id = documentDao.createDocument(docEntity);
		docEntity.setId(id);
		return docEntity;
	}


	@Override
	public void deleteDocumentById(Document document) {
		if(document.getId() == null) return;
		document.setState(1);
		document.setUpdateDate(new Date());
		documentDao.updateDocument(document);
	}


	@Override
	public void updateDocument(Document document) {
		document.setUpdateDate(new Date());
		documentDao.updateDocument(document);
	}


	@Override
	public Document getDocumentById(Document document) {
		
		return documentDao.getDocumentById(document);
	}


	



	
	
	

}
