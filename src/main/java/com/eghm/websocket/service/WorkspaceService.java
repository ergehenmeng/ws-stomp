package com.eghm.websocket.service;

import java.util.List;

import com.eghm.websocket.model.Workspace;

public interface WorkspaceService {

    /**
     * 通过用户Id获取当前用户所能进入的工作空间
     *
     * @param id
     * @return
     */
    List<Workspace> getWorkspaceByUserId(Integer id);

}
