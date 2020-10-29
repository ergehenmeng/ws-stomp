package com.eghm.websocket.model;

public class Coordination {
	
	private Integer id;
	
	private Integer fileId;
	
	private Integer spaceId;
	
	public Coordination(){
		
	}
	
	public Coordination(Integer fileId,Integer spaceId){
		this.fileId = fileId;
		this.spaceId = spaceId;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}
	
	
}
