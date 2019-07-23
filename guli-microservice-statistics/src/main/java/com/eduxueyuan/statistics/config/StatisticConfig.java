package com.eduxueyuan.statistics.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.eduxueyuan.statistics.mapper")
@ComponentScan("com.edu.xueyuan")
public class StatisticConfig implements InitializingBean{

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
