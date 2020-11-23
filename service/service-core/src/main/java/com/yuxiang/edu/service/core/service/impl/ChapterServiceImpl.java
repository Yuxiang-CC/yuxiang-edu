package com.yuxiang.edu.service.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxiang.edu.service.core.entity.Chapter;
import com.yuxiang.edu.service.core.entity.Video;
import com.yuxiang.edu.service.core.entity.vo.ChapterVO;
import com.yuxiang.edu.service.core.entity.vo.VideoVO;
import com.yuxiang.edu.service.core.mapper.ChapterMapper;
import com.yuxiang.edu.service.core.mapper.VideoMapper;
import com.yuxiang.edu.service.core.service.ChapterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<ChapterVO> nestedListById(String id) {
        // 根据课程Id获取所有章节列表信息
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        chapterQueryWrapper.orderByAsc("sort", "id");
        List<Chapter> chapters = baseMapper.selectList(chapterQueryWrapper);

        // 根据课程Id获取所有视频列表信息
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        videoQueryWrapper.orderByAsc("sort", "id");
        List<Video> videos = videoMapper.selectList(videoQueryWrapper);

        // 遍历chapters(章节列表),videos(视频列表） ，进行组装
        List<ChapterVO> chapterVOS = new ArrayList<>();

        chapters.forEach(chapter -> {
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(chapter, chapterVO);
            chapterVOS.add(chapterVO);
        });
        chapterVOS.forEach(chapterVO -> {
            List<VideoVO> videoVOS = new ArrayList<>();
            videos.forEach(video -> {
                if (video.getChapterId().equals(chapterVO.getId())) {
                    VideoVO videoVO = new VideoVO();
                    BeanUtils.copyProperties(video, videoVO);
                    videoVOS.add(videoVO);
                }
            });
            chapterVO.setChildren(videoVOS);
        });
        return chapterVOS;
    }

    @Override
    public boolean removeChapterById(String id) {
        // 删除Video （课时）
        QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("chapter_id", id);
        videoMapper.delete(videoWrapper);
        // 删除章节
        return this.removeById(id);
    }
}
