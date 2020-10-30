package com.eghm.websocket.mapper;

import com.eghm.websocket.model.Document;

public interface DocumentMapper {
    /**
     * 插入数据库记录
     *
     * @param record 条件 
     */
    int insert(Document record);

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(Document record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    Document selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(Document record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKey(Document record);
}