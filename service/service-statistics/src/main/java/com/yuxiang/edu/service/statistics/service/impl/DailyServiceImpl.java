package com.yuxiang.edu.service.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.statistics.entity.Daily;
import com.yuxiang.edu.service.statistics.entity.excel.DownLoadDaily;
import com.yuxiang.edu.service.statistics.feign.UcenterMemberService;
import com.yuxiang.edu.service.statistics.mapper.DailyMapper;
import com.yuxiang.edu.service.statistics.service.DailyService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-22
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @Override
    public void createStatisticsByDay(String date) {

        // 如果当前日期的统计数据已经存在，则先删除
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", date);
        baseMapper.delete(queryWrapper);

        // 获取某一天中的注册人数
        R r = ucenterMemberService.countRegisterNumber(date);
        Integer registerNum = (Integer) r.getData().get("registerNum");
        // TODO 获取其他服务统计信息，可以用 【MQ】 ？
        int loginNum = RandomUtils.nextInt(10, 100);
        int videoViewNum = RandomUtils.nextInt(10, 100);
        int courseNum = RandomUtils.nextInt(10, 100);

        Daily daily = new Daily();
        daily.setRegisterNum(registerNum);
        daily.setCourseNum(courseNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setDateCalculated(date);

        baseMapper.insert(daily);
    }


    @Override
    public List<Daily> getStatisticsByDate(String begin, String end) {

        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isBlank(end)) {
            queryWrapper.gt("date_calculated", begin);
        } else {
            queryWrapper.between("date_calculated", begin, end);
        }

        return baseMapper.selectList(queryWrapper);
    }


    @Override
    public List<DownLoadDaily> exportStatisticsByDate(String begin, String end) {

        return baseMapper.selectExportDaily(begin, end);
    }
}
