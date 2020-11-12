package com.eghm.websocket.service;

import com.eghm.websocket.model.PartSlide;
import com.eghm.websocket.model.Slide;

public interface SlideService {

    /**
     * 保存演示文稿
     *
     * @param slidePart
     */
    void saveSlidePart(PartSlide slidePart);

    /**
     * 通过fileId获取对象
     *
     * @param fileId
     * @return
     */
    Slide getSlideByFileId(Integer fileId);

    /**
     * 创建演示文稿
     *
     * @param name
     * @param spaceId
     * @return
     */
    Integer createSlide(String name, Integer spaceId);

    /**
     * 创建文档页面
     *
     * @param slideId
     * @return
     */
    Integer createPartSlide(Integer slideId);

}
