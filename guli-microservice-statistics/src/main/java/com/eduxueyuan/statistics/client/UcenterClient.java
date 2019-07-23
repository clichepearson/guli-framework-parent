package com.eduxueyuan.statistics.client;


import com.edu.xueyuan.entity.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("guli-ucenter")
public interface UcenterClient {

    @GetMapping("/educenter/ucenter-member/getCountRegister/{day}")
    public R getCountRegister(@PathVariable("day") String day);
}
