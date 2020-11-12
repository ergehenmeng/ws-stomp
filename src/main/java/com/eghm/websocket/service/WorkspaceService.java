package com.eghm.websocket.service;

import com.eghm.websocket.model.Workspace;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface WorkspaceService {

    /**
     * 通过用户Id获取当前用户所能进入的工作空间
     * @param userId userId
     * @return 工作空间
     */
    List<Workspace> getByUserId(Long userId);

}
