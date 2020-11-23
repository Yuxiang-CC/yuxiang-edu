package com.yuxiang.edu.service.ams.feign;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.ams.feign.callback.OssFileServiceCallBack;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: yuxiang
 * @Date: 2020/11/20 10:30
 */
@FeignClient(value = "service-oss", fallback = OssFileServiceCallBack.class)
public interface OssFileService {

    /**
     * 测试远程调用
     * @return
     */
    @ApiOperation("测试 调用OpenFegin")
    @GetMapping("/api/oss/file/test")
    R test();

    /**
     * 远程调用删除讲头像
     * @param url
     * @return
     */
    @ApiOperation("删除讲师头像")
    @DeleteMapping("/api/oss/file/auth/remove-file")
    R removeFile(String url);
}
