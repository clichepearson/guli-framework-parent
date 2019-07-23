package com.edu.xueyuan.eduservice.service;

import com.edu.xueyuan.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.xueyuan.eduservice.entity.subjectDto.OneSubjectDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-08
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<String> saveSubject(MultipartFile file);

    List<OneSubjectDto> listAllSub();


    boolean removeSubById(String id);

    boolean saveLevelOne(EduSubject subject);

    boolean saveLevelTwo(EduSubject subject);
}
