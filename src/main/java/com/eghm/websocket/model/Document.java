package com.eghm.websocket.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
public class Document implements Serializable {

    /**
     * 主键<br>
     * 表 : document<br>
     * 对应字段 : id<br>
     */
    private Long id;

    /**
     * 工作空间id<br>
     * 表 : document<br>
     * 对应字段 : space_id<br>
     */
    private Long spaceId;

    /**
     * <br>
     * 表 : document<br>
     * 对应字段 : doc_name<br>
     */
    private String docName;

    /**
     * 文档类型 word md<br>
     * 表 : document<br>
     * 对应字段 : type<br>
     */
    private String type;

    /**
     * 状态 1:正常 0:删除<br>
     * 表 : document<br>
     * 对应字段 : state<br>
     */
    private Byte state;

    /**
     * 文档密码<br>
     * 表 : document<br>
     * 对应字段 : pwd<br>
     */
    private String pwd;

    /**
     * 是否显示 true:显示 false:隐藏<br>
     * 表 : document<br>
     * 对应字段 : hidden<br>
     */
    private Boolean hidden;

    /**
     * 排序规则<br>
     * 表 : document<br>
     * 对应字段 : sort<br>
     */
    private Integer sort;

    /**
     * 添加时间<br>
     * 表 : document<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : document<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}