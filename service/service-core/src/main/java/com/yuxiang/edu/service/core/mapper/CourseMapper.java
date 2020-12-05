package com.yuxiang.edu.service.core.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuxiang.edu.service.base.dto.CourseDTO;
import com.yuxiang.edu.service.core.entity.Course;
import com.yuxiang.edu.service.core.entity.vo.CourseCollectVO;
import com.yuxiang.edu.service.core.entity.vo.CoursePublishVO;
import com.yuxiang.edu.service.core.entity.vo.CourseVO;
import com.yuxiang.edu.service.core.entity.vo.WebCourseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 根据课程ID查询课程详细信息
     * @param courseId
     * @return
     */
    WebCourseVO selectWebCourseVOById(@Param("courseId") String courseId);

    /**
     * 根据课程ID查询课程
     * @param courseId
     * @return
     */
    CourseDTO selectCourseDTOById(@Param("courseId") String courseId);

    /**
     * 分页查询课程
     * @param pageParam
     * @param queryWrapper
     * @return
     */
    List<CourseVO> selectPageByCourseQueryVO(Page<CourseVO> pageParam,
                                             @Param(Constants.WRAPPER) QueryWrapper<Course> queryWrapper);

    /**
     * 根据ID查询发布课程
     * @param courseId
     * @return
     */
    CoursePublishVO selectCoursePublishById(@Param("courseId") String courseId);


    /**
     * 获取会员收藏课程
     * @param collects
     * @return
     */
    List<CourseCollectVO> selectCourseCollectVOList(@Param("collects") List<String> collects);
}
