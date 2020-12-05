package com.yuxiang.edu.service.vod.controller.admin;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.vod.service.LiveInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yuxiang
 * @Date: 2020/12/1 19:03
 */
@Api(description = "视频直播管理控制类")
@RestController
@RequestMapping("/admin/vod/live")
@Slf4j
public class AdminLiveInfoController {

    @Autowired
    private LiveInfoService liveInfoService;

    @ApiOperation("获取直播间列表信息")
    @GetMapping("/page/info")
    public R pageLiveInfo() {

        return R.ok();
    }
}
