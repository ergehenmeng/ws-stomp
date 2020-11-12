package com.eghm.websocket.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class DocumentFile implements Serializable{

	private Integer id;
	
	private Integer documentId;
	
	private String content;
	
	private Integer userId;
	
	private Integer spaceId;

}
