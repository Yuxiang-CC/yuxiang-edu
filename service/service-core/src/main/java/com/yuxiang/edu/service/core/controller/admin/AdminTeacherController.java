package com.yuxiang.edu.service.core.controller.admin;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.core.entity.Teacher;
import com.yuxiang.edu.service.core.feign.OssFileService;
import com.yuxiang.edu.service.core.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: yuxiang
 * @Date: 2020/11/20 10:40
 */
@CrossOrigin
@Api(description = "讲师管理控制器")
@RestController
@RequestMapping("/admin/core/teacher")
public class AdminTeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private OssFileService ossFileService;

    @ApiOperation("新增讲师")
    @PostMapping("/add")
    public R add(@ApiParam("讲师信息") @RequestBody Teacher teacher) {

        teacher.setJoinDate(new Date());
        boolean result = teacherService.save(teacher);

        if (result) {
            return R.ok().message("新增成功");
        }
        return R.error().message("新增失败");
    }

    @ApiOperation("所有讲师列表")
    @GetMapping("/list")
    public R listAll() {

        List<Teacher> teacherList = teacherService.list();

        return R.ok().data("items", teacherList);
    }

    @ApiOperation(value = "根据ID删除讲师", notes = "详细信息：逻辑删除")
    @DeleteMapping("/remove/{id}")
    public R removeById(@ApiParam("讲师ID") @PathVariable("id") String id) {

        boolean removeAvatarResult = teacherService.removeAvatarById(id);

        boolean result = teacherService.removeById(id);
        if (result) {
            return R.ok().message("删除成功");
        }

        return R.error().message("删除失败");
    }

    @ApiOperation("根据id批量删除讲师")
    @DeleteMapping("/batch-remove")
    public R removeByIds(@ApiParam("讲师ID列表") @RequestBody List<String> idList) {

        boolean result = teacherService.removeByIds(idList);
        if (result) {
            return R.ok().message("操作成功");
        }

        return R.error().message("操作失败");
    }

    @ApiOperation("更新讲师")
    @PutMapping("/update")
    public R update(@ApiParam("讲师对象信息") @RequestBody Teacher teacher) {
        boolean result = teacherService.updateById(teacher);
        if (result) {
            return R.ok().message("更新成功");
        }

        return R.error().message("数据不存在");
    }

    @ApiOperation("根据关键字查询讲师名字")
    @GetMapping("/list-name/{nameKey}")
    public R listName(
            @ApiParam(value = "讲师名字关键字", required = true)
            @PathVariable("nameKey") String nameKey) {

        List<Map<String, Object>> teacherNameList = teacherService.selectByNameList(nameKey);
        return R.ok().data("names", teacherNameList);
    }


}
