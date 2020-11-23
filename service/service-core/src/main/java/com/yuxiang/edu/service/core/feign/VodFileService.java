package com.yuxiang.edu.service.core.feign;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.core.feign.callback.VodFileServiceCallBack;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: yuxiang
 * @create: 2020-11-22 10:39
 **/
@FeignClient(value = "service-vod", fallback = VodFileServiceCallBack.class)
public interface VodFileService {


    @ApiOperation("远程调用删除视频")
    @DeleteMapping("/admin/vod/file/remove/{vodId}")
    R remove(@PathVariable("vodId") String vodId);

    @ApiOperation("远程调用批量删除视频")
    @DeleteMapping("/admin/vod/file/remove")
    R remove(@RequestBody List<String> vodIds);

}
