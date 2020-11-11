package com.eghm.websocket.dto;

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
    private int type;

    public static <T> SocketBody<T> success(int type, T data) {
        SocketBody<T> socketBody = new SocketBody<>();
        socketBody.setType(type);
        socketBody.setDate(data);
        return socketBody;
    }
}
