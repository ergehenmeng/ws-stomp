package com.eghm.websocket.mapper;

import com.eghm.websocket.model.Workspace;

import java.util.List;

public interface WorkspaceMapper {
	
	/**
	 * 通过用户Id获取所有的工作空间
	 * @param id
	 * @return
	 */
	List<Workspace> getWorkspaceByUserId(Integer id);
	
}
