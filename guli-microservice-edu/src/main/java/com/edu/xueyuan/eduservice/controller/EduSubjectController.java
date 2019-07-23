package com.edu.xueyuan.eduservice.controller;


import com.edu.xueyuan.eduservice.entity.EduSubject;
import com.edu.xueyuan.eduservice.entity.subjectDto.OneSubjectDto;
import com.edu.xueyuan.eduservice.service.EduSubjectService;
import com.edu.xueyuan.entity.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-08
 */

@Api(description="课程分类管理")
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    EduSubjectService subjectService;

    @ApiOperation(value = "批量导入课程")
    @PostMapping("saveSub")
    public R saveSub(MultipartFile file) {

        List<String> list = subjectService.saveSubject(file);

        if(list.size() == 0) {

            return R.ok().message("批量导入成功");

        }else{

            return R.error().message("部分导入成功").data("messageList",list);

        }

    }
    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("listAllSub")
    public R listAllSub() {

        List<OneSubjectDto> subjectList = subjectService.listAllSub();

        return R.ok().data("items",subjectList);
    }
    @ApiOperation(value = "删除课程分类")
    @DeleteMapping("deleteSub/{id}")
    public R deleteSub(@PathVariable String id) {

        boolean flag = subjectService.removeSubById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "增加一级分类")
    @PostMapping("saveLevelOne")
    public R saveLevelOne(@RequestBody EduSubject subject) {

        boolean save = subjectService.saveLevelOne(subject);

        if(save) {
            return R.ok();
        }else{
            return R.error();
        }
    }
    @ApiOperation(value = "增加二级分类")
    @PostMapping("saveLevelTwo")
    public R saveLevelTwo(@RequestBody EduSubject subject){

       boolean save = subjectService.saveLevelTwo(subject);

        if(save) {
            return R.ok();
        }else{
            return R.error();
        }
    }

}

