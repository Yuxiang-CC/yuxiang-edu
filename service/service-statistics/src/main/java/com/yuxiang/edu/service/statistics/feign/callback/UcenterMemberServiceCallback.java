package com.yuxiang.edu.service.statistics.feign.callback;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.statistics.feign.UcenterMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 14:51
 */
@Component
@Slf4j
public class UcenterMemberServiceCallback implements UcenterMemberService {

    @Override
    public R countRegisterNumber(String day) {
        log.error("统计注册人数远程服务熔断被调用");
        return R.ok().data("registerNum", 0);
    }
}
