package com.edu.xueyuan.eduservice.controller;










import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.xueyuan.entity.R;
import com.edu.xueyuan.eduservice.entity.EduTeacher;

import com.edu.xueyuan.eduservice.query.TeacherQuery;
import com.edu.xueyuan.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-02
 */

@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("login")
    //{"code":20000,"data":{"token":"admin"}}
    public R login() {

        return R.ok().data("token","admin");

    }

    @GetMapping("info")
    //["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
    //查询所有讲师的信息
    @GetMapping
    public R getAllTeachers() {

        List<EduTeacher> list = teacherService.list(null);

        return R.ok().data("item", list);

    }

    //根据id删除讲师信息
    @DeleteMapping("{id}")
    public R removeById(@PathVariable String id) {

        boolean flag = teacherService.removeById(id);

        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    //查询分页讲师信息，无条件

    @GetMapping("selectTechersByPage/{page}/{limit}")
    public R selectTechersByPage(@PathVariable Long page, @PathVariable Long limit) {

        Page<EduTeacher> pageTeacher = new Page<>(page, limit);

        teacherService.page(pageTeacher, null);

        List<EduTeacher> records = pageTeacher.getRecords();

        long total = pageTeacher.getTotal();

        return R.ok().data("total", total).data("rows", records);


    }

    //查询讲师分页信息，带条件
    @PostMapping("pageQuery/{page}/{limit}")
    public R pageQuery(@PathVariable Long page,
                       @PathVariable Long limit,
                       @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> pageTeacher = new Page<>(page, limit);

        teacherService.pageQuery(pageTeacher, teacherQuery);

        List<EduTeacher> records = pageTeacher.getRecords();

        long total = pageTeacher.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    @PostMapping("saveTeacher")
    public R saveTeacher(@RequestBody EduTeacher teacher) {

        boolean flag = teacherService.save(teacher);

        if (flag) {

            return R.ok();

        } else {

            return R.error();

        }
    }

    //根据id查询teacher

    @GetMapping("selectTeacherById/{id}")
    public R selectTeacherById(@PathVariable String id) {

        EduTeacher teacher = teacherService.getById(id);

        return R.ok().data("item", teacher);
    }


    //根据id修改teacher
    @PostMapping("updateTeacherById")
    public R updateTeacherById(@RequestBody EduTeacher teacher) {

        boolean flag = teacherService.updateById(teacher);

        if (flag) {

            return R.ok();

        } else {

            return R.error();

        }

    }
}

