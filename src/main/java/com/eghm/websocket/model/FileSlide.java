package com.eghm.websocket.model;

public class FileSlide {
	private Integer id;
	
	private Integer fileId;
	
	public FileSlide(){
		
	}
	public FileSlide(Integer fileId){
		this.fileId = fileId;
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
	
	
}
