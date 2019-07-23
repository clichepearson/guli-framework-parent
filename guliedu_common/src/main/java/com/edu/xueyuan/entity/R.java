package com.edu.xueyuan.entity;




import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {
    //返回是否成功
    private String success;
    //返回状态码
    private Integer code;
    //返回消息
    private String message;
    //返回的数据data
    private Map<String,Object> data = new HashMap<>();


    private R(){

    }
    //当操作成功的时候返回OK
    public static R ok() {
        R r = new R();

        r.setSuccess("true");

        r.setCode(ResultCode.OK.getCode());

        r.setMessage(ResultCode.OK.getMessage());

        return r;
    }
    //操作错误的时候返回error
    public static R error() {
        R r = new R();

        r.setSuccess("false");

        r.setCode(ResultCode.ERROR.getCode());

        r.setMessage(ResultCode.ERROR.getMessage());

        return r;
    }

    public R message(String message) {

        this.setMessage(message);

        return  this;
    }

    public R code(Integer code) {

        this.setCode(code);

        return this;
    }

    public R data(String key, Object value) {

        this.data.put(key,value);

        return this;

    }

    public R data(Map<String,Object> map) {

        this.setData(map);

        return this;
    }




}
