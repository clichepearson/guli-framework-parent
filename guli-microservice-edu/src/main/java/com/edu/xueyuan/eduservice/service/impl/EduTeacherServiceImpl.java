package com.edu.xueyuan.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.xueyuan.eduservice.entity.EduTeacher;
import com.edu.xueyuan.eduservice.mapper.EduTeacherMapper;
import com.edu.xueyuan.eduservice.query.TeacherQuery;
import com.edu.xueyuan.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-02
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageTeacher, TeacherQuery teacherQuery) {

        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("gmt_create");
        if(teacherQuery == null){
            baseMapper.selectPage(pageTeacher,queryWrapper);
        }else {
            String name = teacherQuery.getName();

            Integer level = teacherQuery.getLevel();

            String begin = teacherQuery.getBegin();

            String end = teacherQuery.getEnd();

            if (!StringUtils.isEmpty(name)) {

                queryWrapper.like("name", name);
            }

            if (!StringUtils.isEmpty(level)) {

                queryWrapper.eq("level", level);

            }

            if (!StringUtils.isEmpty(begin)) {

                queryWrapper.gt("gmt_create", begin);
            }

            if (!StringUtils.isEmpty(end)) {

                queryWrapper.lt("gmt_modified", end);
            }



            baseMapper.selectPage(pageTeacher, queryWrapper);

        }
    }


    //获取前台分页教师数据
    @Override
    public Map<String, Object> getTeacherPage(Page<EduTeacher> pageTeacher) {

        baseMapper.selectPage(pageTeacher, null);
        //获取总记录数
        long total = pageTeacher.getTotal();
        //获取每页记录
        List<EduTeacher> records = pageTeacher.getRecords();
        //获取每页记录数
        long size = pageTeacher.getSize();
        //获取当前页
        long current = pageTeacher.getCurrent();
        //获取总页数
        long pages = pageTeacher.getPages();

        boolean hasNext = pageTeacher.hasNext();

        boolean hasPrevious = pageTeacher.hasPrevious();

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
}
