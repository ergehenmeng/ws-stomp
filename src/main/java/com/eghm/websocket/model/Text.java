package com.eghm.websocket.model;

public class Text {
	
	private Integer id;
	private String delta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDelta() {
		return delta;
	}

	public void setDelta(String delta) {
		this.delta = delta;
	}

	@Override
	public String toString() {
		return "Text [id=" + id + ", delta=" + delta + "]";
	}
	
	
}
