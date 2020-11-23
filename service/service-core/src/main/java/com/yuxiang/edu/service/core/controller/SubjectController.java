package com.yuxiang.edu.service.core.controller;


import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.core.entity.vo.SubjectVO;
import com.yuxiang.edu.service.core.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 课程分类 前端控制器
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
@CrossOrigin
@Api(description = "课程分类控制类")
@RestController
@RequestMapping("/api/core/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation("课程类别")
    @GetMapping("/nested-list")
    public R nestedList() {

        List<SubjectVO> list = subjectService.nestedList();
        return R.ok().data("items", list);
    }

}

