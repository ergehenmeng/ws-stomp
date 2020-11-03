package com.eghm.websocket.service.impl;

import com.eghm.websocket.enums.FileType;
import com.eghm.websocket.mapper.DocumentMapper;
import com.eghm.websocket.model.Document;
import com.eghm.websocket.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService{
	
	@Autowired
	private DocumentMapper documentMapper;

	@Override
	public List<Document> getByWorkspaceId(Integer workspaceId) {
		return documentMapper.getByWorkspaceId(workspaceId);
	}

    @Override
    public Document createDocument(Integer workspaceId, String docName, FileType type) {
	    Document document = new Document();
        document.setDocName(docName);
        document.setWorkspaceId(workspaceId);
        document.setType(type.getType());
        documentMapper.insertSelective(document);
        return document;
    }

    @Override
	public void deleteById(Integer id) {
		Document document = new Document();
        document.setId(id);
		document.setState((byte)0);
		documentMapper.updateByPrimaryKeySelective(document);
	}

    @Override
    public void setPwd(Integer docId, String pwd) {
        Document document = documentMapper.selectByPrimaryKey(docId);
        // TODO 校验文档是否为当前用户所拥有
        document.setPwd(pwd);
        documentMapper.updateByPrimaryKeySelective(document);
    }

    @Override
    public void updateSelective(Document document) {
        documentMapper.updateByPrimaryKeySelective(document);
    }

    @Override
    public Document getById(Integer docId) {
        return documentMapper.selectByPrimaryKey(docId);
    }
}
