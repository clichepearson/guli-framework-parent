package com.edu.xueyuan.handler;

import com.edu.xueyuan.entity.R;
import com.edu.xueyuan.exception.EduException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R error(EduException e){

        e.printStackTrace();

        return R.error().code(e.getCode()).message(e.getMessage());


    }
}

