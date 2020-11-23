package com.yuxiang.edu.service.vod.controller;


import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.JWTUtils;
import com.yuxiang.edu.common.util.JwtInfo;
import com.yuxiang.edu.service.base.constant.VodConstant;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.vod.entity.LiveInfo;
import com.yuxiang.edu.service.vod.service.LiveInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 直播信息表 前端控制器
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-18
 */
@CrossOrigin
@Api(description = "视频直播管理控制类")
@RestController
@RequestMapping("/api/vod/live")
@Slf4j
public class LiveInfoController {

    @Autowired
    private LiveInfoService liveInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("获取直播间播放地址")
    @GetMapping("/auth/my-live")
    public R getLiveUrl(HttpServletRequest request) {

        // 查询直播间播放地址
        JwtInfo info = JWTUtils.getInfoFromJWT(request);

        return R.ok().data("liveUrl", info.getId());
    }

    @ApiOperation("获取推流地址")
    @GetMapping("/auth/my-push")
    public R getPushUrl(HttpServletRequest request) {
        // 查询缓存获取推流地址
        JwtInfo info = JWTUtils.getInfoFromJWT(request);
        System.out.println(info);
        String pushDomain = (String) redisTemplate.opsForHash().get(
                VodConstant.LIVE_CACHE_PREFIX + info.getId(),
                VodConstant.PUSH);
        System.out.println("pushDomain:" + pushDomain);
        if (StringUtils.isNotBlank(pushDomain)) {
            return R.ok().data("pushDomain", pushDomain);
        } else {
            return R.setResult(ResultCodeEnum.UNKNOWN_REASON);
        }
    }


    @ApiOperation("开通直播间，生成房间号")
    @GetMapping("/auth/create")
    public R create(HttpServletRequest request) {
        JwtInfo info = JWTUtils.getInfoFromJWT(request);

        liveInfoService.save(new LiveInfo().setTid(info.getId()));
        return R.ok();
    }


    @ApiOperation("开播")
    @PostMapping("/auth/play")
    public R play(@ApiParam("开播信息") @RequestBody LiveInfo liveInfo) {
        // 生成直播推流地址、播流地址，并将播流地址存入缓存
        String pushUrl = liveInfoService.play(liveInfo);

        return R.ok().data("pushUrl", pushUrl);
    }

    @ApiOperation("观看直播，获取直播间拉流地址")
    @GetMapping("/live/{liveId}")
    public R getLive(@ApiParam("直播间ID") @PathVariable("liveId") String liveId) {

        // 获取直播间播流地址
        List<String> filedList = new ArrayList<>();
        filedList.add(VodConstant.RMTP);
        filedList.add(VodConstant.M3U8);
        filedList.add(VodConstant.FLV);
        List list = redisTemplate.opsForHash().multiGet(VodConstant.LIVE_CACHE_PREFIX + liveId, filedList);

        // TODO 【需要优化】寻找直播间，获取其分类ID，并将直播间人数增加
        LiveInfo liveInfo = (LiveInfo) redisTemplate.opsForHash().get(VodConstant.LIVE_CACHE_PREFIX + liveId, VodConstant.INFO);

        redisTemplate.opsForZSet().incrementScore(VodConstant.LIVE_CATEGORY_PREFIX + liveInfo.getCategoryId(), liveId, 1);

        return R.ok().data("pullUrl", list);
    }

    @ApiOperation("退出直播间")
    @GetMapping("/auth/quit-live/{liveId}")
    public R quitLive(@ApiParam("直播间ID") @PathVariable("liveId") String liveId) {

        // TODO 【需要优化】寻找直播间，获取其分类ID，并将直播间人数增加
        LiveInfo liveInfo = (LiveInfo) redisTemplate.opsForHash().get(VodConstant.LIVE_CACHE_PREFIX + liveId, VodConstant.INFO);

        redisTemplate.opsForZSet().incrementScore(VodConstant.LIVE_CATEGORY_PREFIX + liveInfo.getCategoryId(), liveId, -1);

        return R.ok();
    }

    @ApiOperation("关闭直播")
    @DeleteMapping("/auth/close")
    public R close(HttpServletRequest request) {
        // 从缓存中删除直播信息
        JwtInfo info = JWTUtils.getInfoFromJWT(request);
        if (info == null) {
            throw new RException(ResultCodeEnum.TOKEN_ERROR);
        }

        // 查询直播分类ID 从缓存分类中删除
        LiveInfo liveInfo = (LiveInfo) redisTemplate.opsForHash().get(VodConstant.LIVE_CACHE_PREFIX + info.getId(), VodConstant.INFO);
        redisTemplate.delete(VodConstant.LIVE_CACHE_PREFIX + info.getId());
        redisTemplate.opsForZSet().remove(VodConstant.LIVE_CATEGORY_PREFIX + liveInfo.getCategoryId(), liveInfo.getTid());

        return R.ok();
    }

    @ApiOperation("直播间列表")
    @GetMapping("/list")
    public R showLives(@ApiParam("分类") @RequestParam("category") String category) {
        // 查询正在直播的直播间 按人气排名,并根据直播间Id查询直播间信息

        if (StringUtils.isBlank(category)) {
            Set set = redisTemplate.opsForZSet().reverseRange(VodConstant.LIVE_RECOMMEND, 0, 15);

            return R.ok().data("lives", set);
        } else {
            Set set = redisTemplate.opsForZSet().reverseRange(VodConstant.LIVE_CATEGORY_PREFIX + category, 0, 15);
            List<LiveInfo> liveInfos = new ArrayList<>(16);

            set.forEach(tid -> {
                LiveInfo liveInfo = (LiveInfo) redisTemplate.opsForHash().get(VodConstant.LIVE_CACHE_PREFIX + tid, VodConstant.INFO);
                liveInfos.add(liveInfo);
            });

            return R.ok().data("lives", liveInfos);
        }

    }
}

