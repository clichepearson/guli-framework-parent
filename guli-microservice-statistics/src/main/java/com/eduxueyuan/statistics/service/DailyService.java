package com.eduxueyuan.statistics.service;

import com.eduxueyuan.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-07-16
 */
public interface DailyService extends IService<Daily> {

    void createStatisticsByDate(String day);

    Map<String,Object> showChart(String begin, String end, String type);
}
