package com.eghm.websocket.model;

import lombok.Data;

import java.util.Date;

@Data
public class Space {
	private Integer id;
	
	private Integer createUserId;
	
	private String spaceName;
	
	private Date createDate;
}
