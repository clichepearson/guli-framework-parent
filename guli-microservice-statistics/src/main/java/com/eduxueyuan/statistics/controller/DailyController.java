package com.eduxueyuan.statistics.controller;


import com.edu.xueyuan.entity.R;
import com.eduxueyuan.statistics.client.UcenterClient;
import com.eduxueyuan.statistics.service.DailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-07-16
 */
@RestController
@RequestMapping("/edustatistics/statistics")
@CrossOrigin
@Api(description = "统计模块")
public class DailyController {
    @Autowired
    DailyService dailyService;

    @ApiOperation(value = "添加统计数据")
    @GetMapping("createStatisticsByDate/{day}")
    public R createStatisticsByDate(@PathVariable("day") String day){

        dailyService.createStatisticsByDate(day);

        return R.ok();
    }
    @ApiOperation(value = "获取图表数据")
    @GetMapping("showChart/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin,
                       @PathVariable String end,
                       @PathVariable String type){

        Map<String,Object> map = dailyService.showChart(begin,end,type);

        return R.ok().data(map);
    }
}

