package com.eghm.websocket.mapper;

import java.util.List;

import com.eghm.websocket.model.Workspace;

public interface WorkspaceDao {
	
	/**
	 * 通过用户Id获取所有的工作空间
	 * @param id
	 * @return
	 */
	public List<Workspace> getWorkspaceByUserId(Integer id);
	
	
}
