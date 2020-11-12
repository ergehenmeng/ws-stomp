package com.eghm.websocket.mapper;

import com.eghm.websocket.model.Space;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpaceMapper {

    /**
     * 插入数据库记录
     *
     * @param record 条件 
     */
    int insert(Space record);

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(Space record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    Space selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(Space record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKey(Space record);

    /**
     * 用户工作空间列表
     * @param userId userId
     * @return
     */
    List<Space> getByUserId(@Param("userId") Long userId);
}