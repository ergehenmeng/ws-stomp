package com.eghm.websocket.dto;

import com.eghm.websocket.enums.ActionType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 殿小二
 * @date 2020/11/11
 */
@Data
public class SocketBody<T> implements Serializable {

    /**
     * 数据
     */
    private T data;

    /**
     * 动作类型
     */
    private ActionType action;

    public static <T> SocketBody<T> success(ActionType type, T data) {
        SocketBody<T> socketBody = new SocketBody<>();
        socketBody.setAction(type);
        socketBody.setData(data);
        return socketBody;
    }
}
