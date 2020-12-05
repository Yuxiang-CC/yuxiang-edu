package com.yuxiang.edu.service.core.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuxiang.edu.service.base.dto.CourseDTO;
import com.yuxiang.edu.service.core.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxiang.edu.service.core.entity.form.CourseInfoForm;
import com.yuxiang.edu.service.core.entity.vo.*;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
public interface CourseService extends IService<Course> {

    /**
     * 根据查询条件查询对象信息 【前台前端界面】
     * @param webCourseQueryVO
     * @return
     */
    List<Course> webSelectList(WebCourseQueryVO webCourseQueryVO);

    /**
     * 获取课程信息，并更新浏览数
     * @param courseId
     * @return
     */
    WebCourseVO getWebCourseVOById(String courseId);

    /**
     * 根据课程ID查询课程信息
     * @param courseId
     * @return
     */
    CourseDTO getCourseDTOById(String courseId);

    /**
     * 查询热门课程
     * @return
     */
    List<Course> selectHotCourse();

    /**
     * 新增课程
     * @param courseInfoForm
     * @return
     */
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    /**
     * 获取课程信息
     * @param courseId
     * @return
     */
    CourseInfoForm getCourseInfoById(String courseId);

    /**
     * 根据课程ID更新课程信息
     * @param courseInfoForm
     */
    void updateCourseInfoById(CourseInfoForm courseInfoForm);

    /**
     * 分页获取课程
     * @param page
     * @param size
     * @param courseQueryVO
     * @return
     */
    Page<CourseVO> selectPage(Long page, Long size, CourseQueryVO courseQueryVO);

    /**
     * 删除课程封面图片
     * @param id
     */
    void removeCourseCoverById(String id);

    /**
     * 根据课程ID删除课程
     * @param id
     * @return
     */
    boolean removeCourseById(String id);

    /**
     * 根据ID查询发布课程
     * @param id
     * @return
     */
    CoursePublishVO getCoursePublishById(String id);

    /**
     * 根据讲师ID发布课程
     * @param id
     * @return
     */
    boolean publishCourseById(String id);
}
