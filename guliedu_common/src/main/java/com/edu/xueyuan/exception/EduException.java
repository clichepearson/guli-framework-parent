package com.edu.xueyuan.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduException extends RuntimeException{
    //状态码
    private Integer code;
    //错误信息
    private String message;
}
