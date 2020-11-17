package com.eghm.websocket.model;

import com.eghm.websocket.annotation.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文档信息表
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
     * 文档名称<br>
     * 表 : document<br>
     * 对应字段 : doc_name<br>
     */
    private String docName;

    /**
     * 文档类型 DOC PPT<br>
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
     * 文件隐藏状态 true:隐藏 false:正常<br>
     * 表 : document<br>
     * 对应字段 : hidden<br>
     */
    private Boolean hidden;

    /**
     * 文档所属用户<br>
     * 表 : document<br>
     * 对应字段 : user_id<br>
     */
    private Long userId;

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

    /**
     * 文档内容<br>
     * 表 : document<br>
     * 对应字段 : content<br>
     */
    private String content;

    /**
     * 用户加密字段
     */
    @Tag
    private String author;

    private static final long serialVersionUID = 1L;

}