package com.yuxiang.edu.service.statistics.mapper;

import com.yuxiang.edu.service.statistics.entity.Daily;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuxiang.edu.service.statistics.entity.excel.DownLoadDaily;

import java.util.List;

/**
 * <p>
 * 网站统计日数据 Mapper 接口
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-22
 */
public interface DailyMapper extends BaseMapper<Daily> {

    /**
     * 获取导出信息
     * @param begin
     * @param end
     * @return
     */
    List<DownLoadDaily> selectExportDaily(String begin, String end);
}
