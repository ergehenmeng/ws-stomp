package com.eghm.websocket.dto;

import com.eghm.websocket.enums.ActionType;
import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/11/11
 */
@Data
public class SocketBody<T> {

    /**
     * 数据
     */
    private T date;

    /**
     * 动作类型
     */
    private ActionType type;

    public static <T> SocketBody<T> success(ActionType type, T data) {
        SocketBody<T> socketBody = new SocketBody<>();
        socketBody.setType(type);
        socketBody.setDate(data);
        return socketBody;
    }
}
