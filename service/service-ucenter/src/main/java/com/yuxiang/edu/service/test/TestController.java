package com.yuxiang.edu.service.test;

import com.yuxiang.edu.service.ucenter.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: yuxiang
 * @Date: 2020/11/15 9:54
 */
//@RefreshScope
@RestController
public class TestController {

    @Value("${config.name:default}")
    private String value;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/test")
    public String test() {
        Member member = new Member();
        member.setId("123123123");
        member.setMobile("1235555555");
        member.setPassword("123hkadhs24jkh3k2h12h3jhadfashasf");
        member.setAge(29);
        member.setGmtCreate(new Date());
        member.setDeleted(true);

        redisTemplate.opsForValue().set("ucenter:" + member.getId(), member);
        Member ucode = (Member) redisTemplate.opsForValue().get("ucenter:" + member.getId());
        return "admin/ucenter/member/test  successful  " + value + " \t\r " + ucode;
    }
}
