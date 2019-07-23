package com.edu.xueyuan.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.xueyuan.eduservice.entity.EduCourse;
import com.edu.xueyuan.eduservice.entity.info.CoursePublish;
import com.edu.xueyuan.eduservice.entity.subjectDto.CourseInfoForm;
import com.edu.xueyuan.eduservice.query.CourseQuery;
import com.edu.xueyuan.eduservice.service.EduCourseService;
import com.edu.xueyuan.entity.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-09
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/edu-course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    EduCourseService eduCourseService;
    @ApiOperation(value = "保存课程信息")
    @PostMapping("saveCourseInfo")
    public R saveCourseInfo(@RequestBody CourseInfoForm course) {

        String courseId = eduCourseService.saveCourseInfo(course);

        if(!StringUtils.isEmpty(courseId)){

            return R.ok().data("courseId",courseId);

        }else{

            return R.error().message("保存失败");

        }




    }
    @ApiOperation(value = "根据id获得课程信息")
    @GetMapping("getCourseInfoById/{id}")
    public R getCourseById(@PathVariable(value = "id") String courseId) {

        CourseInfoForm courseInfoForm = eduCourseService.getCourseInfoById(courseId);

        return R.ok().data("item",courseInfoForm);
    }

    @PostMapping("updateCourseById")
    public R updateCourseById(@RequestBody CourseInfoForm courseInfo) {

        eduCourseService.updateCourseById(courseInfo);

        return R.ok();
    }

    @ApiOperation(value="根据id获取课程发布信息")
    @GetMapping("getCoursePublishById/{courseId}")
    public R getCoursePublishById(@PathVariable String courseId){

        CoursePublish coursePublish = eduCourseService.getCoursePublistById(courseId);

        return R.ok().data("item",coursePublish);
    }
    @ApiOperation(value = "查询所有课程信息带查询条件")
    @PostMapping("pageQuery/{page}/{limit}")
    public R pageQuery(@PathVariable Long page,
                       @PathVariable Long limit,
                       @RequestBody(required = false) CourseQuery courseQuery){

            Page<EduCourse> pageCourse = new Page<>(page,limit);

           eduCourseService.pageQuery(pageCourse,courseQuery);

        List<EduCourse> courseList = pageCourse.getRecords();

        long total = pageCourse.getTotal();

        return R.ok().data("items",courseList).data("total",total);
    }

    @ApiOperation(value = "根据id删除课程信息")
    @DeleteMapping("deleteCourseById/{courseId}")
    public R deleteCourseById(@PathVariable String courseId){

        eduCourseService.deleteCourseById(courseId);

        return R.ok();
    }

    @ApiOperation(value = "根据id修改发布课程状态")
    @PutMapping("updateCourseStatus/{courseId}")
    public R updateCourseStatus(@PathVariable String courseId){

        EduCourse course = new EduCourse();

        course.setId(courseId);

        course.setStatus("Normal");

        eduCourseService.updateById(course);

        return R.ok();
    }
}

