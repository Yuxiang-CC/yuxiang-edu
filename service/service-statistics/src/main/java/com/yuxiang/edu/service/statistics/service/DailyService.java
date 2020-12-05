package com.yuxiang.edu.service.statistics.service;

import com.yuxiang.edu.service.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxiang.edu.service.statistics.entity.excel.DownLoadDaily;

import java.util.List;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-22
 */
public interface DailyService extends IService<Daily> {

    /**
     * 生成某天的统计信息
     * @param day
     */
    void createStatisticsByDay(String day);

    /**
     * 获取某时间范围内的统计信息
     * @param begin
     * @param end
     * @return
     */
    List<Daily> getStatisticsByDate(String begin, String end);


    /**
     * 获取某时间范围内的统计信息
     * @param begin
     * @param end
     * @return
     */
    List<DownLoadDaily> exportStatisticsByDate(String begin, String end);
}
