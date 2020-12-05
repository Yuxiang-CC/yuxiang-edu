package com.yuxiang.edu.service.ams.controller;


import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.ams.entity.Ad;
import com.yuxiang.edu.service.ams.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 广告推荐 前端控制器
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-22
 */
@Api(description = "广告推荐控制器")
@RestController
@RequestMapping("/api/ams/ad")
@Slf4j
public class AdController {

    @Autowired
    private AdService adService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("根据前端传递的广告分类查询广告信息")
    @GetMapping("/list/{adTypeId}")
    public R getAdListByAdType(@ApiParam(value = "推荐位ID", required = true) @PathVariable("adTypeId") String adTypeId) {

        List<Ad> adList =  adService.selectByAdTypeId(adTypeId);

        return R.ok().data("items", adList);
    }


}

