package com.eduxueyuan.educenter;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient

public class UCenterApplication {

    public static void main(String[] args) {

        SpringApplication.run(UCenterApplication.class);
    }
}
