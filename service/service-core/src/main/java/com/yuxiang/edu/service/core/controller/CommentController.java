package com.yuxiang.edu.service.core.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.util.JWTUtils;
import com.yuxiang.edu.common.util.JwtInfo;
import com.yuxiang.edu.service.core.entity.Comment;
import com.yuxiang.edu.service.core.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
@Api(description = "评论管理")
@RestController
@RequestMapping("/api/core/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation("发表评论")
    @PostMapping("/publish")
    public R publish(@ApiParam("课程ID") @RequestParam("courseId") String courseId,
                     @ApiParam("讲师ID") @RequestParam("teacherId") String teacherId,
                     @ApiParam("回复评论的ID") @RequestParam("parentId") String parentId) {

        return R.ok();
    }

    @ApiOperation("删除自己评论")
    @PostMapping("/delete")
    public R delete(HttpServletRequest request) {
        JwtInfo info = JWTUtils.getInfoFromJWT(request);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", info.getId());
        boolean remove = commentService.remove(queryWrapper);
        return R.ok().data("result", remove);
    }

    @ApiOperation("根据课程ID查询部分评论")
    @GetMapping("/page/{page}/{size}")
    public R getByPage(@ApiParam("页数") @PathVariable("page") String page,
                       @ApiParam("数量") @PathVariable("size") String size) {

        return R.ok();
    }

    @ApiOperation("根据课程ID查询部分评论")
    @GetMapping("/two-comment/{id}")
    public R getByParentID(@ApiParam("一级评论ID") @PathVariable("id") String id) {

        return R.ok();
    }


}

