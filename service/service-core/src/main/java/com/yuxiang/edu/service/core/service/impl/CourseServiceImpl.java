package com.yuxiang.edu.service.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.service.base.dto.CourseDTO;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.core.entity.*;
import com.yuxiang.edu.service.core.entity.form.CourseInfoForm;
import com.yuxiang.edu.service.core.entity.vo.*;
import com.yuxiang.edu.service.core.feign.OssFileService;
import com.yuxiang.edu.service.core.mapper.*;
import com.yuxiang.edu.service.core.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;

    @Autowired
    private OssFileService ossFileService;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private CourseCollectMapper courseCollectMapper;

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public List<Course> webSelectList(WebCourseQueryVO webCourseQueryVO) {

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Course.COURSE_NORMAL);

        if (StringUtils.isNotBlank(webCourseQueryVO.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", webCourseQueryVO.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(webCourseQueryVO.getSubjectId())) {
            queryWrapper.eq("subject_id", webCourseQueryVO.getSubjectId());
        }

        if (!StringUtils.isEmpty(webCourseQueryVO.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(webCourseQueryVO.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(webCourseQueryVO.getPriceSort())) {
            if(webCourseQueryVO.getType() == null || webCourseQueryVO.getType() == 1){
                queryWrapper.orderByAsc("price");
            }else{
                queryWrapper.orderByDesc("price");
            }
        }

        return baseMapper.selectList(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WebCourseVO getWebCourseVOById(String courseId) {
        // 1.将浏览量 +1
        Course course = baseMapper.selectById(courseId);
        if (course == null) {
            throw new RException(ResultCodeEnum.COURSE_NOT_EXIST);
        }
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);

        WebCourseVO webCourseVO = baseMapper.selectWebCourseVOById(courseId);

        return webCourseVO;
    }

    @Override
    public CourseDTO getCourseDTOById(String courseId) {

        return baseMapper.selectCourseDTOById(courseId);
    }

    @Cacheable(value = "hot", key = "'course'")
    @Override
    public List<Course> selectHotCourse() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view_count");
        queryWrapper.last("limit 8");

        return baseMapper.selectList(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        // 1. 保存Course
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        course.setStatus(Course.COURSE_DRAFT);
        baseMapper.insert(course);

        // 2. 保存CourseDescription
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionMapper.insert(courseDescription);

        return course.getId();
    }

    @Override
    public CourseInfoForm getCourseInfoById(String courseId) {
        // 1.根据id 获取Course对象
        Course course = baseMapper.selectById(courseId);
        if (course == null) {
            return null;
        }
        // 2.根据Id 获取CourseDescription对象
        CourseDescription courseDescription = courseDescriptionMapper.selectById(courseId);

        // 3.组装成CourseInfoFrom对象
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course, courseInfoForm);
        courseInfoForm.setDescription(courseDescription.getDescription());

        return courseInfoForm;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCourseInfoById(CourseInfoForm courseInfoForm) {

        // 1.Course对象
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        course.setStatus(Course.COURSE_DRAFT);
        baseMapper.updateById(course);

        // 2.CourseDescription
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(courseInfoForm.getId());
        courseDescriptionMapper.updateById(courseDescription);

    }

    @Override
    public Page<CourseVO> selectPage(Long page, Long size, CourseQueryVO courseQueryVO) {

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("c.gmt_create");

        String title = courseQueryVO.getTitle();
        String teacherId = courseQueryVO.getTeacherId();
        String subjectParentId = courseQueryVO.getSubjectParentId();
        String subjectId = courseQueryVO.getSubjectId();

        // 1. 组装QueryWrapper对象
        if (StringUtils.isNotBlank(title)) {
            queryWrapper.likeRight("c.title", title);
        }

        if (StringUtils.isNotBlank(teacherId)) {
            queryWrapper.eq("c.teacher_id", teacherId);
        }

        if (StringUtils.isNotBlank(subjectParentId)) {
            queryWrapper.eq("c.subject_parent_id", subjectParentId);
        }

        if (StringUtils.isNotBlank(subjectId)) {
            queryWrapper.eq("c.subject_id", subjectId);
        }

        // 2. 组装分页
        Page<CourseVO> pageParam = new Page<>(page, size);
        // 3. 执行查询 只需要在Mapper层传入封装好的Page对象，分页信息组装有MP插件自动完成
        List<CourseVO> courseVOs =  baseMapper.selectPageByCourseQueryVO(pageParam, queryWrapper);

        pageParam.setRecords(courseVOs);
        return pageParam;
    }

    @Override
    public void removeCourseCoverById(String id) {
        // 根据id 获取课程封面rul
        Course course = baseMapper.selectById(id);
        if (course == null) {
            return;
        }

        String cover = course.getCover();
        if (StringUtils.isNotBlank(cover)) {
            R r = ossFileService.removeFile(cover);
            System.out.println(r.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeCourseById(final String id) {

        // 1.删除Video （课程视频）
        QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", id);
        videoMapper.delete(videoWrapper);

        // 2.删除Chapter （课程章节）
        QueryWrapper<Chapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", id);
        chapterMapper.delete(chapterWrapper);

        // 3.删除Comment （评论）
        QueryWrapper<Comment> commentWrapper = new QueryWrapper<>();
        commentWrapper.eq("course_id", id);
        commentMapper.delete(commentWrapper);

        // 4.删除CourseCollect （课程收藏）
        QueryWrapper<CourseCollect> courseCollectWrapper = new QueryWrapper<>();
        courseCollectWrapper.eq("course_id", id);
        courseCollectMapper.delete(courseCollectWrapper);

        // 5.删除CourseDescription （课程详情）
        /**
         * 因为CourseDescription与Course表是一对一关系，主键相同，所以可以直接删除
         * QueryWrapper<CourseDescription> descriptionWrapper = new QueryWrapper<>();
         * descriptionWrapper.eq("id", id);
         * courseDescriptionMapper.delete(descriptionWrapper);
         */
        courseDescriptionMapper.deleteById(id);

        // 删除Course
        return this.removeById(id);
    }

    @Override
    public CoursePublishVO getCoursePublishById(String id) {

        return baseMapper.selectCoursePublishById(id);
    }

    @Override
    public boolean publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        return this.updateById(course);
    }
}
