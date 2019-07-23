package com.edu.xueyuan.eduservice.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.xueyuan.eduservice.entity.EduCourse;
import com.edu.xueyuan.eduservice.entity.EduTeacher;
import com.edu.xueyuan.eduservice.service.EduCourseService;
import com.edu.xueyuan.eduservice.service.EduTeacherService;
import com.edu.xueyuan.entity.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(description = "前台讲师模块")
@RequestMapping("/eduservice/front-teacher")
@CrossOrigin
public class FrontTeacherController {
    @Autowired
    EduTeacherService eduTeacherService;
    @Autowired
    EduCourseService eduCourseService;

    @ApiOperation(value = "分页查询讲师信息")
    @GetMapping("getTeacherPage/{page}/{limit}")
    public R getTeacherPage(@PathVariable Long page,
                            @PathVariable Long limit){

        Page<EduTeacher> pageTeacher = new Page<>(page,limit);

        Map<String,Object> map = eduTeacherService.getTeacherPage(pageTeacher);

        return R.ok().data(map);
    }

    @ApiOperation(value = "根据讲师id获取信息及该讲师课程信息")
    @GetMapping("getById/{id}")
    public R getById(@PathVariable String id){
        //获取讲师信息
        EduTeacher teacher = eduTeacherService.getById(id);

        //根据讲师id获取该讲师讲的课程
        List<EduCourse> courseList = eduCourseService.selectByTeacherId(id);

        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }
}
