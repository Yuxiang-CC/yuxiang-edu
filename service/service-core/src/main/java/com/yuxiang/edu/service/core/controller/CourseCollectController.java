package com.yuxiang.edu.service.core.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.JWTUtils;
import com.yuxiang.edu.common.util.JwtInfo;
import com.yuxiang.edu.service.core.entity.CourseCollect;
import com.yuxiang.edu.service.core.entity.vo.CourseCollectVO;
import com.yuxiang.edu.service.core.service.CourseCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 课程收藏 前端控制器
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
@Api(description = "课程收藏控制类")
@RestController
@RequestMapping("/api/core/course-collect")
public class CourseCollectController {

    @Autowired
    private CourseCollectService courseCollectService;

    @ApiOperation("添加收藏")
    @PostMapping("/auth/add/{courseId}")
    public R add(@ApiParam("课程ID") @PathVariable("courseId") String courseId,
                 HttpServletRequest request) {

        JwtInfo info = JWTUtils.getInfoFromJWT(request);
        if (info == null) {
            return R.setResult(ResultCodeEnum.TOKEN_AUTHENTICATION);
        }
        boolean result = courseCollectService.addCourseCollect(info.getId(), courseId);

        if (result) {
            return R.ok().message("收藏成功");
        } else {
            return R.error();
        }
    }

    @ApiOperation("取消收藏")
    @DeleteMapping("/auth/cancel/{courseId}")
    public R delete(@ApiParam("课程ID") @PathVariable("courseId") String courseId,
                 HttpServletRequest request) {

        JwtInfo info = JWTUtils.getInfoFromJWT(request);
        if (info == null) {
            return R.setResult(ResultCodeEnum.DATA_NOT_FOUND);
        }
        boolean result = courseCollectService.cancelCourseCollect(info.getId(), courseId);

        if (result) {
            return R.ok().message("取消收藏成功");
        } else {
            return R.error();
        }
    }


    @ApiOperation("获取收藏列表")
    @GetMapping("/auth/list")
    public R list(HttpServletRequest request) {

        JwtInfo info = JWTUtils.getInfoFromJWT(request);
        if (info == null) {
            return R.setResult(ResultCodeEnum.TOKEN_AUTHENTICATION);
        }
        List<CourseCollectVO> courseCollectList = courseCollectService.getCourseCollectList(info.getId());

        return R.ok().data("items", courseCollectList);
    }

    @ApiOperation("是否已经收藏")
    @GetMapping("/auth/is-collect/{courseId}")
    public R isCollect(@ApiParam("课程ID") @PathVariable("courseId") String courseId,
                       HttpServletRequest request) {

        JwtInfo info = JWTUtils.getInfoFromJWT(request);
        if (info == null) {
            return R.setResult(ResultCodeEnum.TOKEN_AUTHENTICATION);
        }
        QueryWrapper<CourseCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", info.getId());
        queryWrapper.eq("course_id", courseId);
        int count = courseCollectService.count(queryWrapper);

        if (count <= 0) {
            return R.ok().data("isCollect", false);
        }
        return R.ok().data("isCollect", true);
    }

}

