package com.edu.xueyuan.eduservice.service;

import com.edu.xueyuan.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.xueyuan.eduservice.entity.courseDto.ChapterDto;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-10
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterDto> nestedList(String courseId);

    boolean deleteChapter(String id);
}
