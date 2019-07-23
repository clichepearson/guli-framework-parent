package com.edu.xueyuan.eduservice.controller;


import com.edu.xueyuan.eduservice.entity.EduVideo;
import com.edu.xueyuan.eduservice.service.EduChapterService;
import com.edu.xueyuan.eduservice.service.EduVideoService;
import com.edu.xueyuan.entity.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-10
 */
@Api(description= "课时管理")
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    EduVideoService eduVideoService;


    @ApiOperation(value = "添加章节")
    @PostMapping("saveVideo")
    public R saveCourse(@RequestBody EduVideo video) {

        boolean save = eduVideoService.save(video);

        if(save){

            return R.ok().message("添加成功");

        }else{

            return R.error().message("添加失败");

        }

    }
    @ApiOperation(value = "根据id查询小节")
    @GetMapping("getById/{id}")
    public R getById(@PathVariable String id){

        EduVideo eduVideo = eduVideoService.getById(id);

        return R.ok().data("item",eduVideo);
    }
    @ApiOperation(value = "修改小节")
    @PostMapping("updateVideoById")
    public R updateChapterById(@RequestBody EduVideo eduVideo){

        boolean flag = eduVideoService.updateById(eduVideo);

        if(flag){

            return R.ok().message("修改成功");

        }else{

            return R.error().message("修改失败");

        }
    }
    @ApiOperation(value = "根据id删除小节")
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable String id){

        boolean flag = eduVideoService.deleteVideo(id);

        if(flag){

            return R.ok().message("删除成功");

        }else{

            return R.error().message("删除失败");

        }
    }

}

