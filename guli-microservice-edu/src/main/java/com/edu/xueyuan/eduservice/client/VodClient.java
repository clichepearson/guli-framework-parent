package com.edu.xueyuan.eduservice.client;

import com.edu.xueyuan.entity.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("guli-vod")
public interface VodClient {
        //远程调用vod服务删除视频
        @DeleteMapping(value = "/eduservice/vod-video/removeVideo/{videoId}")
        public R removeVideo(@PathVariable("videoId") String videoId);

        //远程调用vod服务批量删除视频
        @DeleteMapping("/eduservice/vod-video/removeVideoList")
        public R removeVideoList(@RequestParam("videoIdList") List<String> videoIdList);
}
