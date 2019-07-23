package com.edu.xueyuan.eduservice.service;

import com.edu.xueyuan.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-10
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean deleteVideo(String id);


}
