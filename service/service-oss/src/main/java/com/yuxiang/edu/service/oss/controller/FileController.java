package com.yuxiang.edu.service.oss.controller;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.ExceptionUtils;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.oss.config.OssProperties;
import com.yuxiang.edu.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: yuxiang
 * @Date: 2020/11/17 13:50
 */
@CrossOrigin
@RestController
@Api(description = "阿里云文件管理")
@RequestMapping("/api/oss/file")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private OssProperties ossProperties;


    @ApiOperation("上传文件")
    @PostMapping("/auth/upload")
    public R uploadImg(
            @ApiParam("文件流") @RequestParam("file") MultipartFile file,
            @ApiParam("文件夹") @RequestParam("module") String module) {

        String uploadUrl = null;
        try {
            uploadUrl = fileService.upload(file.getInputStream(), module, file.getOriginalFilename());
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new RException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
        return R.ok().message("文件上传成功").data("url", uploadUrl);
    }

    @ApiOperation("删除图像文件")
    @DeleteMapping("/auth/remove-file")
    public R removeFile(
            @ApiParam(value = "图片url", required = true)
            @RequestBody String url) {

        fileService.removeFile(url);
        return R.ok().message("文件删除成功");
    }

    @ApiOperation("测试")
    @GetMapping("/test")
    public R test() {

        System.out.println(ossProperties.toString());

        return R.ok().message("Oss /api/oss/file/test 测试成功");
    }
}
