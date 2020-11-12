package com.eghm.websocket.service.impl;

import com.eghm.websocket.mapper.WorkspaceMapper;
import com.eghm.websocket.model.Workspace;
import com.eghm.websocket.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 */
@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private WorkspaceMapper workspaceMapper;

    @Override
    public List<Workspace> getByUserId(Long id) {
        return workspaceMapper.getByUserId(id);
    }

}
