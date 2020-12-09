package com.yuxiang.edu.service.sms.controller;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.FormUtils;
import com.yuxiang.edu.common.util.RandomUtils;
import com.yuxiang.edu.service.base.constant.UcenterConstant;
import com.yuxiang.edu.service.sms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @Author: yuxiang
 * @Date: 2020/11/16 19:22
 */
@Api(description = "阿里云短信管理")
@RestController
@RequestMapping("/api/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("发送验证码：用户登录")
    @GetMapping("/send-login-code/{phone}")
    public R sendLoginCode(@PathVariable("phone") @ApiParam("手机号") String phone) throws Exception {

        // 1.校验手机号是否合法
        if (StringUtils.isBlank(phone) || !FormUtils.isMobile(phone)) {
            return R.setResult(ResultCodeEnum.MOBILE_FORMAT_ERROR);
        }

        // 2.生成验证码
        String sixCode = RandomUtils.getSixBitRandom();

        // 3.发送验证码
        smsService.sendCode(phone, sixCode);

        // 4.存入数据库
        redisTemplate.opsForValue().set(UcenterConstant.LOGIN_PREFIX_KEY + phone, sixCode, 5, TimeUnit.MINUTES);

        return R.ok().message("短信发送成功");

    }

    @ApiOperation("发送验证码：用户注册")
    @GetMapping("/send-register-code/{phone}")
    public R sendRegisterCode(@PathVariable("phone") @ApiParam("手机号") String phone) throws Exception {

        // 1.校验手机号是否合法
        if (StringUtils.isBlank(phone) || !FormUtils.isMobile(phone)) {
            return R.setResult(ResultCodeEnum.MOBILE_FORMAT_ERROR);
        }

        // 2.生成验证码
        String sixCode = RandomUtils.getSixBitRandom();

        // 3.发送验证码
        smsService.sendCode(phone, sixCode);

        // 4.存入数据库
        redisTemplate.opsForValue().set(UcenterConstant.REGISTRY_PREFIX_KEY + phone, sixCode, 5, TimeUnit.MINUTES);

        return R.ok().message("短信发送成功");

    }

    @ApiOperation("发送邮件：用户注册")
    @GetMapping("/send-register-mail-code/{mail}")
    public R sendRegisterMailCode(@PathVariable("mail") @ApiParam("邮箱") String mail) {
        // 1.校验手机号是否合法
        if (StringUtils.isBlank(mail) || !FormUtils.isMail(mail)) {
            return R.setResult(ResultCodeEnum.MOBILE_FORMAT_ERROR);
        }

        // 2.生成验证码
        String sixCode = RandomUtils.getSixBitRandom();

        // 3.发送验证码
        smsService.sendMail(mail, sixCode);

        // 4.存入数据库
        redisTemplate.opsForValue().set(UcenterConstant.REGISTRY_PREFIX_KEY + mail, sixCode, 5, TimeUnit.MINUTES);

        return R.ok().message("邮件发送成功");
    }

}
