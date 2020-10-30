package com.eghm.websocket.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eghm.websocket.mapper.WorkspaceMapper;
import com.eghm.websocket.model.Workspace;
import com.eghm.websocket.service.WorkspaceService;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Resource
    private WorkspaceMapper workspaceMapper;

    @Override
    public List<Workspace> getWorkspaceByUserId(Integer id) {
        return workspaceMapper.getWorkspaceByUserId(id);
    }

}
