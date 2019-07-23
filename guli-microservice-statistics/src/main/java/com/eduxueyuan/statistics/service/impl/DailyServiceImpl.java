package com.eduxueyuan.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.xueyuan.entity.R;
import com.eduxueyuan.statistics.client.UcenterClient;
import com.eduxueyuan.statistics.entity.Daily;
import com.eduxueyuan.statistics.mapper.DailyMapper;
import com.eduxueyuan.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-07-16
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {
    @Autowired
    UcenterClient ucenterClient;
    //添加统计数据
    @Override
    public void createStatisticsByDate(String day) {

        //删除已存在的统计对象
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("date_calculated",day);

        baseMapper.delete(queryWrapper);

        //获取统计信息

        R r = ucenterClient.getCountRegister(day);

        Integer countRegister = (Integer)r.getData().get("countRegister");

        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO

        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO

        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象

        Daily daily = new Daily();

        daily.setDateCalculated(day);

        daily.setLoginNum(loginNum);

        daily.setVideoViewNum(videoViewNum);

        daily.setCourseNum(courseNum);

        daily.setRegisterNum(countRegister);

        baseMapper.insert(daily);
    }


    //获取图表数据
    @Override
    public Map<String, Object> showChart(String begin, String end, String type) {

        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();

        queryWrapper.between("date_calculated",begin,end);

        queryWrapper.select(type,"date_calculated");
        //获取符合条件的所有数据
        List<Daily> dailyList = baseMapper.selectList(queryWrapper);

        Map<String,Object> map = new HashMap<>();
        //用来存放日期数据
        List<String> dateList = new ArrayList<>();
        //用来存放类型记录数据
        List<Integer> typeList = new ArrayList<>();

        map.put("dateList",dateList);

        map.put("typeList",typeList);

        for (int i = 0; i < dailyList.size(); i++) {

            Daily daily = dailyList.get(i);

            String date = daily.getDateCalculated();
            //封装日期数据
            dateList.add(date);

            //封装类型记录数据
            switch (type){

                case "login_num":
                    Integer loginNum = daily.getLoginNum();
                    typeList.add(loginNum);
                    break;
                case "register_num" :
                    Integer registerNum = daily.getRegisterNum();
                    typeList.add(registerNum);
                    break;
                case "video_view_num" :
                    Integer videoViewNum = daily.getVideoViewNum();
                    typeList.add(videoViewNum);
                    break;
                case "course_num" :
                    Integer courseNum = daily.getCourseNum();
                    typeList.add(courseNum);
            }
        }


        return map;
    }
}
