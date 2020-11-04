package com.eghm.websocket.config.web;

import com.eghm.websocket.dto.RespBody;
import com.eghm.websocket.exception.SystemException;
import com.eghm.websocket.exception.WebSocketException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 殿小二
 * @date 2020/10/30
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(SystemException.class)
    public RespBody<Object> handleException(HttpServletRequest request, SystemException ex){
        log.warn("业务异常 [{}] [{}] [{}]", request.getRequestURI(), ex.getCode(), ex.getMessage());
        return RespBody.error(ex.getCode(), ex.getMessage());
    }

    /**
     * websocket异常
     */
    @MessageExceptionHandler(WebSocketException.class)
    public RespBody<Object> webSocketException(HttpServletRequest request, WebSocketException ex) {
        log.warn("websocket业务异常 [{}] [{}] [{}]", request.getRequestURI(), ex.getCode(), ex.getMessage());
        return RespBody.error(ex.getCode(), ex.getMessage());
    }

}
