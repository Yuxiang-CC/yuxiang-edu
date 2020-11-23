package com.yuxiang.edu.service.statistics.task;

import cn.hutool.core.date.DateTime;
import com.yuxiang.edu.service.statistics.service.DailyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 14:57
 */
@Component
@Slf4j
public class StatisticsTask {

    @Autowired
    private DailyService dailyService;

    @Scheduled(cron = "1/3 * * * * * *")
    public void test1() {
        log.info("task1 被执行");
    }

    @Scheduled(cron = "0 0 1 * * ? *")
    public void createStatistics() {

        log.info("成生统计信息");
        String day = new DateTime().toString("yyyy-MM-dd");
        dailyService.createStatisticsByDay(day);

    }
}