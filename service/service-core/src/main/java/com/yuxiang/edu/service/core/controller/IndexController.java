package com.yuxiang.edu.service.core.controller;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.core.entity.Course;
import com.yuxiang.edu.service.core.entity.Teacher;
import com.yuxiang.edu.service.core.feign.OssFileService;
import com.yuxiang.edu.service.core.service.CourseService;
import com.yuxiang.edu.service.core.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 9:39
 */
@Api(description="首页控制类")
@RestController
@RequestMapping("/api/core/index")
public class IndexController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private OssFileService ossFileService;

    @ApiOperation("课程和讲师的首页数据")
    @GetMapping("/hot")
    public R index(){

        //查询热门课程
        List<Course> courseList = courseService.selectHotCourse();

        //查询推荐讲师
        List<Teacher> teacherList = teacherService.selectHotTeacher();

        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }

    @ApiOperation("链路调用测试")
    @GetMapping("/test")
    public R test() {

        R r = ossFileService.test();
        r.message(r.getMessage() + "\t Service-Core 被调用。。。");
        return r;
    }
}
