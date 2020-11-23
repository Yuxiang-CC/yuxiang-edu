package com.yuxiang.edu.service.vod.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Author: yuxiang
 * @Date: 2020/11/17 16:13
 */
public interface VideoService {

    /**
     * 上传视频至阿里云
     * @param inputStream
     * @param originalFilename
     */
    String uploadVideo(InputStream inputStream, String originalFilename);

    /**
     * 获取视频播放地址
     * @param vid
     * @return
     */
    List<Map<String, Object>> getVideoUrl(String vid);

    /**
     * 获取播放凭证
     * @param vid
     * @return
     */
    String getVideoPlayAuth(String vid);

    /**
     * 删除视频
     * @param vid
     */
    void removeVideo(String vid);

    /**
     * 批量删除视频
     * @param vodIds
     */
    void removeVideo(List<String> vodIds);
}
