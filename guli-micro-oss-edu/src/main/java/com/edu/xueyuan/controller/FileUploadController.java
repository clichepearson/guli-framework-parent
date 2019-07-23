package com.edu.xueyuan.controller;


import com.edu.xueyuan.eduoss.ConstantPropertiesUtil;
import com.edu.xueyuan.entity.R;
import com.edu.xueyuan.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/edu-oss")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("upload")
    public R upload(MultipartFile file,@RequestParam(value = "host",required = false) String host) {

        if(!StringUtils.isEmpty(host)){
            ConstantPropertiesUtil.FILE_HOST = host;
        }
        String uploadFileUrl = fileUploadService.upload(file);

        return R.ok().message("上传成功").data("url",uploadFileUrl);
    }



}
