package com.edu.xueyuan.eduservice.entity.subjectDto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubjectDto {

    private String id;

    private String title;

    private List<TwoSubjectDto> children = new ArrayList<>();
}
