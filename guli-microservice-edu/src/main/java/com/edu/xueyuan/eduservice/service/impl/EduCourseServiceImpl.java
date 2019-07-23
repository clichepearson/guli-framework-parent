package com.edu.xueyuan.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.xueyuan.eduservice.client.VodClient;
import com.edu.xueyuan.eduservice.entity.EduChapter;
import com.edu.xueyuan.eduservice.entity.EduCourse;
import com.edu.xueyuan.eduservice.entity.EduCourseDescription;
import com.edu.xueyuan.eduservice.entity.EduVideo;
import com.edu.xueyuan.eduservice.entity.info.CourseFront;
import com.edu.xueyuan.eduservice.entity.info.CoursePublish;
import com.edu.xueyuan.eduservice.entity.subjectDto.CourseInfoForm;
import com.edu.xueyuan.eduservice.mapper.EduCourseMapper;
import com.edu.xueyuan.eduservice.query.CourseQuery;
import com.edu.xueyuan.eduservice.service.EduChapterService;
import com.edu.xueyuan.eduservice.service.EduCourseDescriptionService;
import com.edu.xueyuan.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.xueyuan.eduservice.service.EduVideoService;
import com.edu.xueyuan.exception.EduException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-09
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    EduChapterService eduChapterService;
    @Autowired
    EduVideoService eduVideoService;
    @Autowired
    VodClient vodClient;
    @Override
    public String saveCourseInfo(CourseInfoForm course) {

        //保存课程信息
        EduCourse eduCourse = new EduCourse();
        //讲表单传递的封装信息拷贝到eduCourse中
        BeanUtils.copyProperties(course,eduCourse);

        int insert = baseMapper.insert(eduCourse);
        //得到courseId给课程详细信息赋值id
        String courseId = eduCourse.getId();
        //课程信息保存失败
        if(insert <=0){
            throw new EduException(20001,"课程信息保存失败");
        }

        //保存课程详细信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();

        BeanUtils.copyProperties(course,eduCourseDescription);
        //给课程描述id赋值
        eduCourseDescription.setId(courseId);

        eduCourseDescriptionService.save(eduCourseDescription);



        return courseId;
    }
    //根据id获取课程详细信息
    @Override
    public CourseInfoForm getCourseInfoById(String courseId) {

        EduCourse eduCourse = baseMapper.selectById(courseId);

        if(eduCourse == null){

            throw new EduException(20001,"数据不存在");
        }

        CourseInfoForm courseInfoForm = new CourseInfoForm();
        //将eduCourse数据拷贝到courseInfoForm中
        BeanUtils.copyProperties(eduCourse,courseInfoForm);

        EduCourseDescription description = eduCourseDescriptionService.getById(courseId);

        if(description == null){
            return courseInfoForm;
        }
        courseInfoForm.setDescription(description.getDescription());

        return courseInfoForm;
    }
    //根据id修改课程信息
    @Override
    public void updateCourseById(CourseInfoForm courseInfo) {

        EduCourse course = new EduCourse();
        //从dto中拷贝数据到实体类中
        BeanUtils.copyProperties(courseInfo,course);
        //把id属性设置到course中去

        int save = baseMapper.updateById(course);
        //添加失败
        if(save <= 0){
            throw new EduException(20001,"修改失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();

        BeanUtils.copyProperties(eduCourseDescription,courseInfo);
        //课程描述表和课程表的id需要达到一致
        eduCourseDescription.setId(course.getId());

        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    //根据获取课程发布信息
    @Override
    public CoursePublish getCoursePublistById(String courseId) {

        CoursePublish coursePublish = baseMapper.selectCoursePublistById(courseId);

        return coursePublish;
    }


    //查询所有课程信息带条件
    @Override
    public void pageQuery(Page<EduCourse> pageCourse, CourseQuery courseQuery) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("gmt_create");

        if(courseQuery == null){
            baseMapper.selectPage(pageCourse,queryWrapper);
        }else{
            String title = courseQuery.getTitle();

            String status = courseQuery.getStatus();

            if(!StringUtils.isEmpty(title)){
                queryWrapper.like("title",title);
            }
            if(!StringUtils.isEmpty(status)){
                queryWrapper.eq("status",status);
            }

           baseMapper.selectPage(pageCourse,queryWrapper);
        }

    }

    //根据课程id删除课程信息
    @Override
    public void deleteCourseById(String courseId) {

        //删除课程

        baseMapper.deleteById(courseId);

        //删除课程章节

        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();

        eduChapterQueryWrapper.eq("course_id",courseId);

        eduChapterService.remove(eduChapterQueryWrapper);


        //删除课时视频
        QueryWrapper<EduVideo> eduVideoQueryWrapper1 = new QueryWrapper<>();

        eduVideoQueryWrapper1.eq("course_id",courseId);

        eduVideoQueryWrapper1.select("video_source_id");
        //根据课程id查询该课程的所有小节
        List<EduVideo> videoList = eduVideoService.list(eduVideoQueryWrapper1);
        //用来存放取出来的视频id
        List<String> videoIdList = new ArrayList<>();

        for (EduVideo eduVideo : videoList) {

            String videoSourceId = eduVideo.getVideoSourceId();

            videoIdList.add(videoSourceId);
        }

        if(videoIdList.size() > 0){

            vodClient.removeVideoList(videoIdList);

        }
        //删除课时

        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();

        eduVideoQueryWrapper.eq("course_id",courseId);

        eduVideoService.remove(eduVideoQueryWrapper);



        //删除课程描述

        eduCourseDescriptionService.removeById(courseId);
    }


    //根据教师id查询课程信息
    @Override
    public List<EduCourse> selectByTeacherId(String id) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("teacher_id",id);

        queryWrapper.orderByDesc("gmt_modified");

        List<EduCourse> courses = baseMapper.selectList(queryWrapper);

        return courses;
    }

    //前台查询课程分页信息
    @Override
    public Map<String, Object> getCoursePage(Page<EduCourse> coursePage) {

        baseMapper.selectPage(coursePage, null);
        //获取总记录数
        long total = coursePage.getTotal();
        //获取每页记录
        List<EduCourse> records = coursePage.getRecords();
        //获取每页记录数
        long size = coursePage.getSize();
        //获取当前页
        long current = coursePage.getCurrent();
        //获取总页数
        long pages = coursePage.getPages();

        boolean hasNext = coursePage.hasNext();

        boolean hasPrevious = coursePage.hasPrevious();

        Map<String,Object> map = new HashMap<>();

        map.put("items", records);

        map.put("current", current);

        map.put("pages", pages);

        map.put("size", size);

        map.put("total", total);

        map.put("hasNext", hasNext);

        map.put("hasPrevious", hasPrevious);

        return map;
    }

    //查询课程信息和讲师信息
    @Override
    public CourseFront selectCourseById(String id) {

        CourseFront courseFront = baseMapper.selectCourseById(id);

        return courseFront;
    }
}
