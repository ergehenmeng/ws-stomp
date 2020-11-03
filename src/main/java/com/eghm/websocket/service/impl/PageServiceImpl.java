package com.eghm.websocket.service.impl;

import com.eghm.websocket.mapper.PageMapper;
import com.eghm.websocket.model.Page;
import com.eghm.websocket.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/11/3
 */
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private PageMapper pageMapper;

    @Override
    public Page getById(Integer id) {
        return pageMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updatePage(Integer id, String content) {
        Page page = new Page();
        page.setId(id);
        page.setContent(content);
        pageMapper.updateByPrimaryKeySelective(page);
    }

}
