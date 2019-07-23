package com.edu.xueyuan.eduservice.controller;


import com.edu.xueyuan.eduservice.entity.EduChapter;
import com.edu.xueyuan.eduservice.entity.courseDto.ChapterDto;
import com.edu.xueyuan.eduservice.service.EduChapterService;
import com.edu.xueyuan.entity.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-10
 */
@Api(description = "章节管理")
@RestController
@RequestMapping("/eduservice/edu-chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    EduChapterService eduChapterService;

    @ApiOperation(value = "嵌套循环列表")
    @GetMapping("nestedList/{courseId}")
    public R nestedListByCourseId(@PathVariable String courseId) {

       List<ChapterDto> chapterDtoList = eduChapterService.nestedList(courseId);

       return R.ok().data("items",chapterDtoList);

    }

    @ApiOperation(value = "添加章节")
    @PostMapping("saveChapter")
    public R saveChapter(@RequestBody EduChapter chapter) {

        boolean save = eduChapterService.save(chapter);

        if(save){

            return R.ok().message("添加成功");

        }else{

            return R.error().message("添加失败");

        }

    }
    @ApiOperation(value = "根据id查询章节")
    @GetMapping("getById/{id}")
    public R getById(@PathVariable String id){

        EduChapter chapter = eduChapterService.getById(id);

        return R.ok().data("item",chapter);
    }
    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapterById")
    public R updateChapterById(@RequestBody EduChapter chapter){

        boolean flag = eduChapterService.updateById(chapter);

        if(flag){

            return R.ok().message("修改成功");

        }else{

            return R.error().message("修改失败");

        }
    }
    @ApiOperation(value = "根据id删除章节")
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable String id){

        boolean flag = eduChapterService.deleteChapter(id);

        if(flag){

            return R.ok().message("删除成功");

        }else{

            return R.error().message("删除失败");

        }
    }
}

