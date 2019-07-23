package com.edu.xueyuan.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.xueyuan.eduservice.entity.EduChapter;
import com.edu.xueyuan.eduservice.entity.EduVideo;
import com.edu.xueyuan.eduservice.entity.courseDto.ChapterDto;
import com.edu.xueyuan.eduservice.entity.courseDto.VideoDto;
import com.edu.xueyuan.eduservice.mapper.EduChapterMapper;
import com.edu.xueyuan.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.xueyuan.eduservice.service.EduVideoService;
import com.edu.xueyuan.exception.EduException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-10
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService eduVideoService;

    @Override
    //根据课程id获取章节，小节列表
    public List<ChapterDto> nestedList(String courseId) {

        //1 获取所有章节列表
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("course_id",courseId);

        queryWrapper.orderByAsc("sort");

        List<EduChapter> chapterList = baseMapper.selectList(queryWrapper);

        //2 获取所有小节列表

        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();

        eduVideoQueryWrapper.eq("course_id",courseId);

        eduVideoQueryWrapper.orderByAsc("sort");

        List<EduVideo> videoList = eduVideoService.list(eduVideoQueryWrapper);

        //该集合用来封装最终的数据

        List<ChapterDto> finalList = new ArrayList<>();

        for (int i = 0; i < chapterList.size(); i++) {

            EduChapter chapter = chapterList.get(i);

            ChapterDto chapterDto = new ChapterDto();

            BeanUtils.copyProperties(chapter,chapterDto);

            finalList.add(chapterDto);

            List<VideoDto> videoDtoList = new ArrayList<>();

            for (int m = 0; m < videoList.size(); m++) {

                EduVideo video = videoList.get(m);

                if(video.getChapterId().equals(chapter.getId())){

                    VideoDto videoDto = new VideoDto();

                    BeanUtils.copyProperties(video,videoDto);

                    videoDtoList.add(videoDto);
                }
            }
            chapterDto.setChildren(videoDtoList);
        }
        return finalList;
    }

    @Override
    //根据课程id删除章节
    public boolean deleteChapter(String id) {

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("chapter_id",id);

        int count = eduVideoService.count(queryWrapper);
        //说明有章节
        if(count >0) {
            throw new EduException(20001,"无法删除有小节的章节");
        }

         baseMapper.deleteById(id);

        return true;
    }
}
