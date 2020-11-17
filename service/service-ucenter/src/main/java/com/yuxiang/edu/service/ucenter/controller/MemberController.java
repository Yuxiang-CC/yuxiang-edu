package com.yuxiang.edu.service.ucenter.controller;


import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.JWTUtils;
import com.yuxiang.edu.common.util.JwtInfo;
import com.yuxiang.edu.service.base.constant.UcenterConstant;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.ucenter.entity.vo.LoginVO;
import com.yuxiang.edu.service.ucenter.entity.vo.RegisterVO;
import com.yuxiang.edu.service.ucenter.service.MemberService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-15
 */
@Api(value = "会员表 前端控制器", description = "会员表")
@Slf4j
@RestController
@RequestMapping("/api/ucenter/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("会员注册：使用手机号码")
    @PostMapping("/phone-register")
    public R phoneRegister(@RequestBody RegisterVO registerVO) {
        if (StringUtils.isEmpty(registerVO.getMobile())) {
            return R.setResult(ResultCodeEnum.MOBILE_REGISTER_ERROR);
        }
        memberService.register(registerVO, UcenterConstant.MOBILE_LOGO);
        return R.ok().message("注册成功");
    }
    @ApiOperation("会员注册：使用邮箱")
    @PostMapping("/mail-register")
    public R mailRegister(@RequestBody RegisterVO registerVO) {
        if (StringUtils.isEmpty(registerVO.getMail())) {
            return R.setResult(ResultCodeEnum.MAIL_REGISTER_ERROR);
        }
        memberService.register(registerVO, UcenterConstant.MAIL_LOGO);
        return R.ok().message("注册成功");
    }

    @ApiOperation("手机账号密码登录")
    @PostMapping("/phone-login")
    public R phoneLogin(@ApiParam("登录实体类") @RequestBody LoginVO loginVO) {

        if (StringUtils.isEmpty(loginVO.getMobile())) {
            return R.setResult(ResultCodeEnum.MOBILE_REGISTER_ERROR);
        }
        String token = memberService.login(loginVO, UcenterConstant.MOBILE_LOGO);
        return R.ok().data("token", token);
    }


    @ApiOperation("邮箱账号密码登录")
    @PostMapping("/mail-login")
    public R mailLogin(@ApiParam("登录实体类") @RequestBody LoginVO loginVO) {

        if (StringUtils.isEmpty(loginVO.getMail())) {
            return R.setResult(ResultCodeEnum.MAIL_REGISTER_ERROR);
        }
        String token = memberService.login(loginVO, UcenterConstant.MAIL_LOGO);
        return R.ok().data("token", token);
    }

    @ApiOperation("手机验证码登录")
    @PostMapping("/phone-code-login")
    public R phoneCodeLogin(@ApiParam("登录实体类") @RequestBody LoginVO loginVO) {

        if (StringUtils.isEmpty(loginVO.getMobile()) || StringUtils.isEmpty(loginVO.getPassword())) {
            return R.setResult(ResultCodeEnum.MOBILE_REGISTER_ERROR);
        }
        String token = memberService.login(loginVO, UcenterConstant.MOBILE_CODE);
        return R.ok().data("token", token);
    }

    @ApiOperation("根据token获取登录信息")
    @GetMapping("/token-info")
    public R getLoginInfo(HttpServletRequest request) {

        try {
            JwtInfo infoFromJWT = JWTUtils.getInfoFromJWT(request);
            return R.ok().data("userInfo", infoFromJWT);
        }catch (Exception e) {
            log.error("解析用户信息失败," + e.getMessage());
            throw new RException(ResultCodeEnum.TOKEN_ERROR);
        }
    }

}

