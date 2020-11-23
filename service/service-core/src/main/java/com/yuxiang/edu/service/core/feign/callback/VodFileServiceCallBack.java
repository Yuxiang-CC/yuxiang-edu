package com.yuxiang.edu.service.core.feign.callback;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.core.feign.VodFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 10:41
 */
@Service
@Slf4j
public class VodFileServiceCallBack implements VodFileService {
    @Override
    public R remove(String vodId) {
        log.info("远程调用服务失败，触发熔断保护");
        return R.error().message("远程调用服务失败，触发熔断保护");
    }

    @Override
    public R remove(List<String> vodIds) {
        log.info("远程调用服务失败，触发熔断保护");
        return R.error().message("远程调用服务失败，触发熔断保护");
    }
}
