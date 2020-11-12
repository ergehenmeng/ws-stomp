package com.eghm.websocket.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
public class Page implements Serializable {
    /**
     * <br>
     * 表 : page<br>
     * 对应字段 : id<br>
     */
    private Long id;

    /**
     * 文档id<br>
     * 表 : page<br>
     * 对应字段 : document_id<br>
     */
    private Long documentId;

    /**
     * 文档所属空间id<br>
     * 表 : page<br>
     * 对应字段 : space_id<br>
     */
    private Long spaceId;

    /**
     * 添加时间<br>
     * 表 : page<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : page<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 内容信息<br>
     * 表 : page<br>
     * 对应字段 : content<br>
     */
    private String content;

    private static final long serialVersionUID = 1L;
}