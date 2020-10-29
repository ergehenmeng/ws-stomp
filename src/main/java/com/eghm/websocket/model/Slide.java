package com.eghm.websocket.model;

import java.util.List;

public class Slide {
	private Integer id;
	
	private Integer fielId;
	
	private List<PartSlide> part;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFielId() {
		return fielId;
	}

	public void setFielId(Integer fielId) {
		this.fielId = fielId;
	}

	public List<PartSlide> getPart() {
		return part;
	}

	public void setPart(List<PartSlide> part) {
		this.part = part;
	}

	
	
	
}
