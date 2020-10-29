package com.eghm.websocket.model;

import java.io.Serializable;

public class DocumentFile implements Serializable{
	
	private static final long serialVersionUID = 832893184430288813L;

	private Integer id;
	
	private Integer documentId;
	
	private String content;
	
	private Integer userId;
	
	private Integer workspaceId;
	

	public Integer getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(Integer workspaceId) {
		this.workspaceId = workspaceId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
