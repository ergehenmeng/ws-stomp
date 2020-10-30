package com.eghm.websocket.mapper;

import com.eghm.websocket.model.Page;

public interface PageMapper {

    /**
     * 插入数据库记录
     *
     * @param record 条件 
     */
    int insert(Page record);

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(Page record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    Page selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(Page record);

    /**
     *
     * @param record 条件 
     */
    int updateByPrimaryKeyWithBLOBs(Page record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKey(Page record);
}