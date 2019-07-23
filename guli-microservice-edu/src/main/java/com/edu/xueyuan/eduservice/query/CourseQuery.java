package com.edu.xueyuan.eduservice.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseQuery {

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "视频状态 Draft未发布  Normal已发布")
    private String status;
}
