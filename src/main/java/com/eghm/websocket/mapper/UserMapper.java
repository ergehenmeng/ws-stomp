package com.eghm.websocket.mapper;

import com.eghm.websocket.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * 插入数据库记录
     *
     * @param record 条件 
     */
    int insert(User record);

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(User record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    User selectByPrimaryKey(Long id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKey(User record);

    /**
     * 手机号码查询用户
     * @param mobile mobile
     * @return user
     */
    User getByMobile(@Param("mobile") String mobile);
}