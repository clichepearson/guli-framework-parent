package com.edu.xueyuan.eduservice.entity.courseDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoDto {

    @ApiModelProperty(value = "视频ID")
    private String id;

    @ApiModelProperty(value = "节点名称")
    private String title;

    @ApiModelProperty(value = "视频资源")
    private String videoSourceId;

}
