package com.yuxiang.edu.service.core.service;

import com.yuxiang.edu.service.core.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
public interface VideoService extends IService<Video> {

    /**
     * 删除课程对应的视频
     * @param courseId
     */
    void removeVodVideoByCourseId(String courseId);

    /**
     * 根据视频ID删除视频
     * @param videoId
     */
    void removeVodVideoById(String videoId);

    /**
     * 根据章节ID删除视频
     * @param chapterId
     */
    void removeVodVideoByChapterId(String chapterId);
}
