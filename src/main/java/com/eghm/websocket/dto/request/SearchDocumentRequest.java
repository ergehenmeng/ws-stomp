package com.eghm.websocket.dto.request;

import com.eghm.websocket.enums.OrderType;
import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/11/14
 */
@Data
public class SearchDocumentRequest {

    /**
     * 工作空间
     */
    private Long spaceId;

    /**
     * 查询条件 文档名称
     */
    private String docName;

    /**
     * 是否显示隐藏文件
     */
    private Boolean showHidden;

    /**
     * 排序字段 可能为空
     */
    private String orderColumn;

    /**
     * 排序规则
     */
    private OrderType orderType;
}
