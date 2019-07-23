package com.edu.xueyuan.eduservice.mapper;

import com.edu.xueyuan.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.xueyuan.eduservice.entity.info.CourseFront;
import com.edu.xueyuan.eduservice.entity.info.CoursePublish;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2019-07-09
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublish selectCoursePublistById(String id);

    CourseFront selectCourseById(String id);

}
