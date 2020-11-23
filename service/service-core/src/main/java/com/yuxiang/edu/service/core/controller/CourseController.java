package com.yuxiang.edu.service.core.controller;


import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.base.dto.CourseDTO;
import com.yuxiang.edu.service.core.entity.Course;
import com.yuxiang.edu.service.core.entity.vo.ChapterVO;
import com.yuxiang.edu.service.core.entity.vo.WebCourseQueryVO;
import com.yuxiang.edu.service.core.entity.vo.WebCourseVO;
import com.yuxiang.edu.service.core.service.ChapterService;
import com.yuxiang.edu.service.core.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
@Api(description = "课程控制类")
@RestController
@RequestMapping("/api/core/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("课程列表")
    @GetMapping("/list")
    public R pageList(
            @ApiParam(value = "查询对象", required = true)
                    WebCourseQueryVO webCourseQueryVO
    ) {

        List<Course> courseList = courseService.webSelectList(webCourseQueryVO);

        return R.ok().data("courseList", courseList);
    }

    @ApiOperation("根据课程ID查询课程信息")
    @GetMapping("/inner/course-dto/{courseId}")
    public CourseDTO getCourseDTOById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable("courseId") String courseId
    ) {

        return courseService.getCourseDTOById(courseId);

    }

    @ApiOperation("课程信息")
    @GetMapping("get/{id}")
    public R courseInfo(
            @ApiParam(value = "查询对象", required = true)
            @PathVariable("id") String id
    ) {

        WebCourseVO webCourseVOBy = courseService.getWebCourseVOById(id);

        // 获取视频章节列表
        List<ChapterVO> chapterVOList = chapterService.nestedListById(id);

        return R.ok().data("course", webCourseVOBy).data("chapterVOList", chapterVOList);
    }


}

