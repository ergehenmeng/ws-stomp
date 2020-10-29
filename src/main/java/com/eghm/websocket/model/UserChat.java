package com.eghm.websocket.model;

public class UserChat {
	
	/**
	 * 用户id
	 */
	public Integer id ;
	
	/**
	 * 用户昵称
	 */
	private String nickName;
	
	/**
	 * 聊天信息
	 */
	private String chatContent;
	
	/**
	 * 文档空间ID
	 */
	private Integer documentId;
	
	private String createTime;
	
	/**
	 * 消息类型
	 */
	private Integer type;
	
	
	
	private Integer workspaceId;
	
	
	public Integer getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(Integer workspaceId) {
		this.workspaceId = workspaceId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getChatContent() {
		return chatContent;
	}

	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	
	
	
}
