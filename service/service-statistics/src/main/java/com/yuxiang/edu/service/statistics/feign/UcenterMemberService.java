package com.yuxiang.edu.service.statistics.feign;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.statistics.feign.callback.UcenterMemberServiceCallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 14:50
 */
@FeignClient(value = "service-ucenter", fallback = UcenterMemberServiceCallback.class)
public interface UcenterMemberService {

    @GetMapping("/admin/ucenter/member/count/{day}")
    R countRegisterNumber(@PathVariable("day") String day);
}
