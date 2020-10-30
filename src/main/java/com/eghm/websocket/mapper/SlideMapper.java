package com.eghm.websocket.mapper;


import com.eghm.websocket.model.FilePartSlide;
import com.eghm.websocket.model.FileSlide;
import com.eghm.websocket.model.PartSlide;
import com.eghm.websocket.model.Slide;

public interface SlideMapper {
	
	/**
	 * 保存演示文稿
	 * @param slidePart
	 */
	void saveSlidePart(PartSlide slidePart);
	
	/**
	 * 通过fileId获取对象
	 * @param fileId
	 * @return
	 */
	Slide getSlideByFileId(Integer fileId);
	
	
	/**
	 * 保存文稿
	 * @param fileSlide
	 * @return
	 */
	Integer saveFileSlide(FileSlide fileSlide);
	
	/**
	 * 保存
	 * @param partSlide
	 * @return
	 */
	Integer saveFilePartSlide(FilePartSlide partSlide);
	
}
