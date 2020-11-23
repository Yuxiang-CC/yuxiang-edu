package com.yuxiang.edu.service.vod.controller;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.ExceptionUtils;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.vod.config.VodProperties;
import com.yuxiang.edu.service.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: yuxiang
 * @Date: 2020/11/17 16:13
 */
@CrossOrigin
@RestController
@Api(description = "阿里云视频点播管理")
@RequestMapping("/api/vod/video")
@Slf4j
public class VideoController {

    @Autowired
    private VodProperties vodProperties;

    @Autowired
    private VideoService videoService;

    @ApiOperation("视频上传")
    @PostMapping("/auth/upload")
    public R uploadVideo(@ApiParam("文件") @RequestParam("file") MultipartFile file) {
        String vid = "";
        try {
            vid = videoService.uploadVideo(file.getInputStream(), file.getOriginalFilename());
        } catch (IOException e) {
            ExceptionUtils.getMessage(e);
            throw new RException(ResultCodeEnum.VIDEO_UPLOAD_TOMCAT_ERROR);
        }
        return R.ok().data("vid", vid);
    }


    @ApiOperation("获取视频播放地址")
    @GetMapping("/auth/video-url/{vid}")
    public R getVideoUrl(@ApiParam("阿里云视频vid") @PathVariable("vid") String vid) {

        List<Map<String, Object>> videoUrls = videoService.getVideoUrl(vid);

        return R.ok().data("videoUrl", videoUrls);
    }


    @ApiOperation("获取播放凭证")
    @GetMapping("/video-play-auth/{vid}")
    public R getVideoPlayAuth(@ApiParam("阿里云视频vid") @PathVariable("vid") String vid) {

        String videoPlayAuth = videoService.getVideoPlayAuth(vid);
        return R.ok().data("videoPlayAuth", videoPlayAuth);
    }

    @ApiOperation("删除视频")
    @DeleteMapping("/auth/remove/{vid}")
    public R removeVideo(@ApiParam("阿里云视频vid") @PathVariable("vid") String vid) {

        videoService.removeVideo(vid);
        return R.ok().message("删除成功");
    }

    @ApiOperation("批量删除视频")
    @DeleteMapping("/auth/remove")
    public R removeVideos(@ApiParam(value = "阿里云视频vid", required = true)
                          @RequestBody List<String> vodIds) {
        videoService.removeVideo(vodIds);
        return R.ok().message("删除成功");
    }


    @ApiOperation("测试")
    @GetMapping("/test")
    public R test() {
        System.out.println(vodProperties);
        return R.ok();
    }

}
