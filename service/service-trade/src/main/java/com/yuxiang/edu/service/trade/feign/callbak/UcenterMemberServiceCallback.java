package com.yuxiang.edu.service.trade.feign.callbak;

import com.yuxiang.edu.service.base.dto.MemberDTO;
import com.yuxiang.edu.service.trade.feign.UcenterMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 15:03
 */
@Slf4j
@Component
public class UcenterMemberServiceCallback implements UcenterMemberService {
    @Override
    public MemberDTO getMemberDTOById(String memberId) {
        log.info("熔断保护");
        return null;
    }
}
