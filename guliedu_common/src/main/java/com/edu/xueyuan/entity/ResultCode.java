package com.edu.xueyuan.entity;

public enum ResultCode {

    OK(20000,"成功"),ERROR(20001,"失败");

    private Integer code;

    private String message;

    private ResultCode(Integer code,String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
