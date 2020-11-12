package com.eghm.websocket.service.impl;

import com.eghm.websocket.mapper.SpaceMapper;
import com.eghm.websocket.model.Space;
import com.eghm.websocket.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 */
@Service
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    private SpaceMapper spaceMapper;

    @Override
    public List<Space> getByUserId(Long id) {
        return spaceMapper.getByUserId(id);
    }

}
