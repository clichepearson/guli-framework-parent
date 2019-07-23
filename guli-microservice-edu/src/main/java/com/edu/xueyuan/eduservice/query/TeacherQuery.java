package com.edu.xueyuan.eduservice.query;

import lombok.Data;

@Data
public class TeacherQuery {
    //教师名称
    private String name;
    //教师级别
    private Integer level;
    //查询开始时间
    private String begin;
    //查询结束时间
    private String end;
}
