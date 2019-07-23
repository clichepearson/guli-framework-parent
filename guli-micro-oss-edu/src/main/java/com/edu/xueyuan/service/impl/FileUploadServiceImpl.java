package com.edu.xueyuan.service.impl;


import com.aliyun.oss.OSSClient;
import com.edu.xueyuan.eduoss.ConstantPropertiesUtil;
import com.edu.xueyuan.service.FileUploadService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@Service
public class FileUploadServiceImpl implements FileUploadService{

    /**
     * 此方法用来实现将文件上传至阿里云，并且返回文件路径
     * @param file
     * @return fileUploadUrl
     */
    @Override
    public String upload(MultipartFile file) {

        //获取阿里云信息相关常量
        //获取地域url
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String fileHost = ConstantPropertiesUtil.FILE_HOST;
        //初始化文件路径值
        String uploadUrl = null;



        // 上传文件流。
        InputStream inputStream = null;
        try {

            // 创建OSSClient实例。
            OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
            //获取文件上传流
            inputStream = file.getInputStream();
            //获取上传文件路径
            String filePath = new DateTime().toString("yyyy/MM/dd");

            String original = file.getOriginalFilename();

            String fileName = UUID.randomUUID().toString();

            String fileType = original.substring(original.lastIndexOf("."));

            String newName = fileName + fileType;

            String fileUrl = fileHost + "/" + filePath + "/" + newName;


            // putObject三个参数：第一个参数是BucketName，第二个产生式是文件名称，第三个参数是输入流
            ossClient.putObject(bucketName, fileUrl, inputStream);
            // 关闭OSSClient。

            ossClient.shutdown();
            //获取文件上传url
            uploadUrl = "https://" + bucketName + "." + endPoint + "/" + fileUrl;


        }  catch (IOException e) {
            e.printStackTrace();
        }

        return uploadUrl;
    }
}
