package com.edu.xueyuan.eduservice.entity.info;


import lombok.Data;

@Data
public class CoursePublish {

    private String title;  //课程标题
    private String cover;   //课程封面
    private Integer lessonNum;  //课时数
    private String subjectLevelOne; //课程章节
    private String subjectLevelTwo; //课程小结
    private String teacherName;     //课程讲师
    private String price;//只用于显示
}
