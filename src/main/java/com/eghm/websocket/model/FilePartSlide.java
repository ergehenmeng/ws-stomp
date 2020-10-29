package com.eghm.websocket.model;

public class FilePartSlide {
	private Integer id;
	
	private Integer slideId;
	
	private String content;
	
	public FilePartSlide(){
		
	}
	public FilePartSlide(Integer slideId, String content){
		this.slideId = slideId;
		this.content = content;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSlideId() {
		return slideId;
	}

	public void setSlideId(Integer slideId) {
		this.slideId = slideId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
