package com.eghm.websocket.service;

import com.eghm.websocket.model.Page;

/**
 * @author 殿小二
 * @date 2020/11/3
 */
public interface PageService {

    /**
     * 主键查询
     *
     * @param id key
     * @return 页
     */
    Page getById(Long id);

    /**
     * 更新页内容
     *
     * @param id      key
     * @param content content
     */
    void updatePage(Long id, String content);
}
