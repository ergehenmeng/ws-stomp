package com.eghm.websocket.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
public class Space implements Serializable {

    /**
     * 主键<br>
     * 表 : space<br>
     * 对应字段 : id<br>
     */
    private Long id;

    /**
     * 命名空间的名称<br>
     * 表 : space<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 命名空间的公告<br>
     * 表 : space<br>
     * 对应字段 : notice<br>
     */
    private String notice;

    /**
     * 该工作空间所属的用户id<br>
     * 表 : space<br>
     * 对应字段 : user_id<br>
     */
    private Long userId;

    /**
     * <br>
     * 表 : space<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : space<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}