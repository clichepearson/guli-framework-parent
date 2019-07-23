package com.edu.xueyuan.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface FileUploadService {

    /**
     * 将文件上传至阿里云
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
