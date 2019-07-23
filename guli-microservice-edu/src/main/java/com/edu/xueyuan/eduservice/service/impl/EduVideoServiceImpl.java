package com.edu.xueyuan.eduservice.service.impl;

import com.edu.xueyuan.eduservice.client.VodClient;
import com.edu.xueyuan.eduservice.entity.EduVideo;
import com.edu.xueyuan.eduservice.mapper.EduVideoMapper;
import com.edu.xueyuan.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-10
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    VodClient vodClient;
    @Override
    //删除小节
    public boolean deleteVideo(String id) {
        //根据小节id查询视频id
        EduVideo eduVideo = baseMapper.selectById(id);
        //获取视频id
        String videoId = eduVideo.getVideoSourceId();

        //如果存在，则删除
        if(!StringUtils.isEmpty(videoId)){

            vodClient.removeVideo(videoId);

        }


        baseMapper.deleteById(id);

        return true;
    }


}
