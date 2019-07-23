package com.edu.vod.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.edu.vod.service.VideoService;
import com.edu.vod.utils.AliyunVodSDKUtils;
import com.edu.vod.utils.ConstantPropertiesUtils;
import com.edu.xueyuan.entity.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Api(description = "阿里云视频点播微服务")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/vod-video")
public class VideoAdminController {
    @Autowired
    VideoService videoService;

    @PostMapping("upload")
    public R uploadVideo( MultipartFile file){

        String videoId = videoService.uploadVideo(file);

        return R.ok().message("上传成功").data("videoId",videoId);

    }

    @ApiOperation(value = "删除阿里云视频")
    @DeleteMapping("removeVideo/{videoId}")
    public R removeVideo(@PathVariable("videoId") String videoId){

        videoService.removeVideo(videoId);

        return R.ok().message("删除成功");

    }

    @DeleteMapping("removeVideoList")
    public R removeVideoList(@RequestParam("videoIdList") List<String> videoIdList){

        videoService.removeVideoList(videoIdList);

        return R.ok().message("批量删除成功");
    }

    @ApiOperation(value = "获取阿里云视频播放凭证")
    @GetMapping("getVideoPlayAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable String videoId){
        try{
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);

            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            request.setVideoId(videoId);

            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            String playAuth = response.getPlayAuth();

            return R.ok().data("playAuth",playAuth);
        }catch (Exception e){

            e.printStackTrace();

            return null;
        }



    }
}
