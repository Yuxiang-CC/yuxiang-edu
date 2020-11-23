package com.yuxiang.edu.service.core.controller;


import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.core.entity.vo.ChapterVO;
import com.yuxiang.edu.service.core.service.ChapterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
@CrossOrigin
@RestController
@RequestMapping("/api/core/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("嵌套章节列表")
    @GetMapping("/nested-list/{courseId}")
    public R nestedListById(
            @ApiParam(value = "课程Id", required = true)
            @PathVariable("courseId") String courseId) {

        List<ChapterVO> chapterVOList = chapterService.nestedListById(courseId);

        return R.ok().data("items", chapterVOList);

    }

}

