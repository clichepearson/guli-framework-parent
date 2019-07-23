package com.edu.xueyuan.eduservice.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.xueyuan.eduservice.entity.EduCourse;
import com.edu.xueyuan.eduservice.entity.courseDto.ChapterDto;
import com.edu.xueyuan.eduservice.entity.info.CourseFront;
import com.edu.xueyuan.eduservice.service.EduChapterService;
import com.edu.xueyuan.eduservice.service.EduCourseService;
import com.edu.xueyuan.entity.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/front-course")
@CrossOrigin
@Api(description = "前台课程模块")
public class FrontCourseController {
    @Autowired
    EduCourseService eduCourseService;
    @Autowired
    EduChapterService eduChapterService;

    @ApiOperation(value = "获取所有课程信息")
    @GetMapping("getCoursePage/{page}/{limit}")
    public R getCoursePage(@PathVariable Long page,
                           @PathVariable Long limit){

        Page<EduCourse> coursePage = new Page<>(page,limit);

        Map<String,Object> map = eduCourseService.getCoursePage(coursePage);

        return R.ok().data(map);
    }

    @ApiOperation(value = "根据id获取课程详细信息")
    @GetMapping("getCourseById/{id}")
    public R getCourseById(@PathVariable String id){

        //查询当前课程信息和讲师信息
        CourseFront course  = eduCourseService.selectCourseById(id);
        //查询当前课程章节信息和小节信息
        List<ChapterDto> chapterDtos = eduChapterService.nestedList(id);


        return R.ok().data("course",course).data("chapterVoList",chapterDtos);
    }

}
