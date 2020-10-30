package com.eghm.websocket.config.web;

import com.eghm.websocket.exception.SystemException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 殿小二
 * @date 2020/10/30
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(SystemException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(SystemException ex){
        return new ModelAndView().addObject("msg",ex.getMessage()).addObject("result", false);
    }
}
