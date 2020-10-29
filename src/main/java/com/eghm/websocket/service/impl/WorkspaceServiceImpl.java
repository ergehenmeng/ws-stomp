package com.eghm.websocket.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eghm.websocket.mapper.WorkspaceDao;
import com.eghm.websocket.model.Workspace;
import com.eghm.websocket.service.WorkspaceService;

@Service("workspaceService")
public class WorkspaceServiceImpl implements WorkspaceService {
	
	@Resource
	private WorkspaceDao workspaceDao;
	
	@Override
	public List<Workspace> getWorkspaceByUserId(Integer id) {
		
		return workspaceDao.getWorkspaceByUserId(id);
	}


}
