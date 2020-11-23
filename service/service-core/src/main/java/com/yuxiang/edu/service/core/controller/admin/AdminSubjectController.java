package com.yuxiang.edu.service.core.controller.admin;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.ExceptionUtils;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.core.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 11:02
 */
@CrossOrigin
@Slf4j
@Api(description = "课程分类控制器")
@RestController
@RequestMapping("/admin/core/subject")
public class AdminSubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation("Excel批量导入课程分类")
    @PostMapping("/import")
    public R batchImport(@ApiParam(value = "Excel文件流", required = true) @RequestParam("file") MultipartFile file) {
        try {
            subjectService.batchImport(file.getInputStream());
        } catch (IOException e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new RException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
        return R.ok().message("批量导入成功");
    }

    // TODO 未完成 课程分类导出Excel、增删改

}
