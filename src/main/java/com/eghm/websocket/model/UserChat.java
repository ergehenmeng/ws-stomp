package com.eghm.websocket.model;

import lombok.Data;

@Data
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

}
