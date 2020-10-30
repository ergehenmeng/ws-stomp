package com.eghm.websocket.mapper;

import com.eghm.websocket.model.Workspace;

public interface WorkspaceMapper {

    /**
     * 插入数据库记录
     *
     * @param record 条件 
     */
    int insert(Workspace record);

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(Workspace record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    Workspace selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(Workspace record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKey(Workspace record);
}