package com.yuxiang.edu.service.trade.feign;

import com.yuxiang.edu.service.base.dto.MemberDTO;
import com.yuxiang.edu.service.trade.feign.callbak.UcenterMemberServiceCallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 15:02
 */
@FeignClient(value = "service-ucenter", fallback = UcenterMemberServiceCallback.class)
public interface UcenterMemberService {

    @GetMapping("/api/ucenter/member/inner/member-dto/{memberId}")
    MemberDTO getMemberDTOById(@PathVariable("memberId") String memberId);
}