package com.eghm.websocket.mapper;


import com.eghm.websocket.model.Coordination;

public interface CoordinationMapper {

    /**
     * 通过id获取文件
     *
     * @param id
     * @return
     */
    Coordination getCoordinationById(Integer id);

    /**
     * 保存对象
     *
     * @param coordination
     * @return
     */
    Integer saveCoorination(Coordination coordination);
}
