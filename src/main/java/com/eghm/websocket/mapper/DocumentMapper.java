package com.eghm.websocket.mapper;

import com.eghm.websocket.model.Document;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据工作空间查询文档
     * @param spaceId  spaceId
     * @param column 排序字段
     * @param orderType 排序类型
     * @return list
     */
    List<Document> getBySpaceId(@Param("spaceId") Integer spaceId,
                                @Param("column") String column,
                                @Param("orderType") String orderType);
}