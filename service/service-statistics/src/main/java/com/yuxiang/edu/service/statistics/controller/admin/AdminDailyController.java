package com.yuxiang.edu.service.statistics.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.statistics.entity.Daily;
import com.yuxiang.edu.service.statistics.service.DailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yuxiang
 * @Date: 2020/11/23 14:35
 */
@CrossOrigin
@Api(description = "统计控制器")
@RestController
@RequestMapping("/admin/statistics/daily")
@Slf4j
public class AdminDailyController {

    @Autowired
    private DailyService dailyService;

    @ApiOperation("根据日期成生统计信息")
    @GetMapping("/create/{day}")
    public R createStatisticsByDay(@ApiParam("统计日期") @PathVariable("day") String day) {

        dailyService.createStatisticsByDay(day);
        return R.ok().message("统计信息生成成功");
    }

    @ApiOperation("获取某天的统计信息")
    @GetMapping("/get/{day}")
    public R getStatisticsByDay(@ApiParam("统计日期") @PathVariable("day") String day) {

        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        Daily daily = dailyService.getOne(queryWrapper);
        return R.ok().data("daily", daily);
    }

}
