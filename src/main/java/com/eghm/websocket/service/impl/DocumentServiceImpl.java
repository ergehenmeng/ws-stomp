package com.eghm.websocket.service.impl;

import com.eghm.websocket.enums.FileType;
import com.eghm.websocket.mapper.DocumentMapper;
import com.eghm.websocket.model.Document;
import com.eghm.websocket.service.DocumentService;
import com.eghm.websocket.utils.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 */
@Service
public class DocumentServiceImpl implements DocumentService{
	
	@Autowired
	private DocumentMapper documentMapper;

	@Autowired
    private KeyGenerator keyGenerator;

    @Override
    public List<Document> getBySpaceId(Long spaceId, String orderColumn, String orderType) {
        return documentMapper.getBySpaceId(spaceId, orderColumn, orderType);
    }

    @Override
    public Document createDocument(Long spaceId, String docName, FileType type) {
	    Document document = new Document();
	    document.setId(keyGenerator.generateKey().longValue());
        document.setDocName(docName);
        document.setSpaceId(spaceId);
        document.setType(type.getType());
        documentMapper.insertSelective(document);
        return document;
    }

    @Override
	public void deleteById(Long id) {
		Document document = new Document();
        document.setId(id);
		document.setState((byte)0);
		documentMapper.updateByPrimaryKeySelective(document);
	}

    @Override
    public void setPwd(Long docId, String pwd) {
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
    public Document getById(Long docId) {
        return documentMapper.selectByPrimaryKey(docId);
    }
}
