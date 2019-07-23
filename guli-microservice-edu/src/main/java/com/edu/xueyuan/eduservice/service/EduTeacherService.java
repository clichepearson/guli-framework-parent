package com.edu.xueyuan.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.xueyuan.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.xueyuan.eduservice.query.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-02
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageQuery(Page<EduTeacher> pageTeacher, TeacherQuery teacherQuery);

    Map<String,Object> getTeacherPage(Page<EduTeacher> pageTeacher);
}
