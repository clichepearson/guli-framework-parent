package com.edu.xueyuan.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.xueyuan.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.xueyuan.eduservice.entity.info.CourseFront;
import com.edu.xueyuan.eduservice.entity.info.CoursePublish;
import com.edu.xueyuan.eduservice.entity.subjectDto.CourseInfoForm;
import com.edu.xueyuan.eduservice.query.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-09
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoForm course);

    CourseInfoForm getCourseInfoById(String courseId);

    void updateCourseById(CourseInfoForm courseInfo);

    CoursePublish getCoursePublistById(String courseId);

    void pageQuery(Page<EduCourse> pageCourse, CourseQuery courseQuery);

    void deleteCourseById(String courseId);

    List<EduCourse> selectByTeacherId(String id);

    Map<String,Object> getCoursePage(Page<EduCourse> coursePage);

    CourseFront selectCourseById(String id);
}
