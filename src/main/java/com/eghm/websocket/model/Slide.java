package com.eghm.websocket.model;

import lombok.Data;

import java.util.List;

@Data
public class Slide {
	private Integer id;
	
	private Integer fielId;
	
	private List<PartSlide> part;

}
