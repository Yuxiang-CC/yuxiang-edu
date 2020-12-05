package com.yuxiang.edu.service.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxiang.edu.service.core.entity.CourseCollect;
import com.yuxiang.edu.service.core.entity.vo.CourseCollectVO;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
public interface CourseCollectService extends IService<CourseCollect> {

    /**
     * 添加收藏
     * @param memberId 会员ID
     * @param courseId 课程ID
     * @return
     */
    boolean addCourseCollect(String memberId, String courseId);

    /**
     * 取消收藏
     * @param memberId
     * @param courseId
     * @return
     */
    boolean cancelCourseCollect(String memberId, String courseId);

    /**
     * 获取会员收藏课程列表
     * @param memberId
     * @return
     */
    List<CourseCollectVO> getCourseCollectList(String memberId);
}
