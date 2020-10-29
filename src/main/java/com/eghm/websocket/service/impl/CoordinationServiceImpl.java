package com.eghm.websocket.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eghm.websocket.mapper.CoordinationMapper;
import com.eghm.websocket.model.Coordination;
import com.eghm.websocket.service.CoordinationService;

@Service("coordinationService")
public class CoordinationServiceImpl implements CoordinationService {
	
	@Resource
	private CoordinationMapper coordinationMapper;
	
	@Override
	public Coordination getCoordinationById(Integer id) {
		
		return coordinationMapper.getCoordinationById(id);
	}
	
	
	
}
