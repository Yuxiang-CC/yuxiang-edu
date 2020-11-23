package com.yuxiang.edu.service.core.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.core.entity.Teacher;
import com.yuxiang.edu.service.core.entity.vo.TeacherQueryVO;
import com.yuxiang.edu.service.core.feign.OssFileService;
import com.yuxiang.edu.service.core.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
@CrossOrigin
@Api(description = "讲师控制类")
@RestController
@RequestMapping("/api/core/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private OssFileService ossFileService;

    @ApiOperation("测试调用service-oss服务")
    @GetMapping("/test-oss")
    public R testOss() {

        return ossFileService.test();
    }

    @ApiOperation("根据ID获取讲师信息")
    @GetMapping("/get/{id}")
    public R getById(@ApiParam("讲师ID") @PathVariable("id") String id) {

        Teacher teacher = teacherService.getById(id);
        if (teacher != null) {
            return R.ok().data("teacher", teacher);
        }

        return R.error().message("数据不存在");
    }


    @ApiOperation(value = "分页获取讲师列表")
    @GetMapping("/list/{page}/{size}")
    public R listPage(@ApiParam(value = "当前页码", required = true) @PathVariable("page") Long page,
                      @ApiParam(value = "页面大小", required = true) @PathVariable("size") Long size,
                      @ApiParam("讲师列表查询对象") TeacherQueryVO teacherQueryVO) {

        Page<Teacher> pageParam = new Page<>(page, size);
        Page<Teacher> pageModelList = teacherService.selectPage(pageParam, teacherQueryVO);

        return R.ok().data("rows", pageModelList.getRecords())
                .data("total", pageModelList.getTotal());
    }
}

