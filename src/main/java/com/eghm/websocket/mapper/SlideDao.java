package com.eghm.websocket.mapper;


import com.eghm.websocket.model.FilePartSlide;
import com.eghm.websocket.model.FileSlide;
import com.eghm.websocket.model.PartSlide;
import com.eghm.websocket.model.Slide;

public interface SlideDao {
	
	/**
	 * 保存演示文稿
	 * @param slidePart
	 */
	public void saveSlidePart(PartSlide slidePart);
	
	/**
	 * 通过fileId获取对象
	 * @param fileId
	 * @return
	 */
	public Slide getSlideByFileId(Integer fileId);
	
	
	/**
	 * 保存文稿
	 * @param fileSlide
	 * @return
	 */
	public Integer saveFileSlide(FileSlide fileSlide);
	
	/**
	 * 保存
	 * @param partSlide
	 * @return
	 */
	public Integer saveFilePartSlide(FilePartSlide partSlide);
	
}
