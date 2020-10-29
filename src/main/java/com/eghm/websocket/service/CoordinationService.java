package com.eghm.websocket.service;

import com.eghm.websocket.model.Coordination;

public interface CoordinationService {
	
	/**
	 * 通过id获取文件
	 * @param id
	 * @return
	 */
	Coordination  getCoordinationById(Integer id);
}
