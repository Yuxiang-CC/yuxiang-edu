package com.yuxiang.edu.service.core.service;

import com.yuxiang.edu.service.core.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxiang.edu.service.core.entity.vo.ChapterVO;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 根据课程ID获取课程章节和课程视频信息
     * @param id
     * @return
     */
    List<ChapterVO> nestedListById(String id);

    /**
     * 根据章节ID删除章节信息
     * @param id
     * @return
     */
    boolean removeChapterById(String id);
}
