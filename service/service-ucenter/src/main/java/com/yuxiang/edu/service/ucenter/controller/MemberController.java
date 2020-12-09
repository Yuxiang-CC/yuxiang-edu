package com.yuxiang.edu.service.ucenter.controller;


import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.FormUtils;
import com.yuxiang.edu.common.util.JWTUtils;
import com.yuxiang.edu.common.util.JwtInfo;
import com.yuxiang.edu.service.base.constant.UcenterConstant;
import com.yuxiang.edu.service.base.dto.MemberDTO;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.ucenter.config.WeiboProperties;
import com.yuxiang.edu.service.ucenter.entity.vo.LoginVO;
import com.yuxiang.edu.service.ucenter.entity.vo.MemberVO;
import com.yuxiang.edu.service.ucenter.entity.vo.RegisterVO;
import com.yuxiang.edu.service.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-15
 */
@CrossOrigin
@Api(value = "会员表 前端控制器", description = "会员表")
@Slf4j
@Controller
@RequestMapping("/api/ucenter/member")
public class MemberController {

    /**
     * 微博登录授权回调地址
     */
    private static final String RETURN_URL = "http://9gjcpu.natappfree.cc/api/ucenter/member/weibo/return";

    @Autowired
    private WeiboProperties weiboProperties;

    @Autowired
    private MemberService memberService;

    @ApiOperation("测试访问")
    @ResponseBody
    @GetMapping("/test")
    public R test() {

        return R.ok().message("测试访问");
    }

    @ResponseBody
    @ApiOperation("手机验证码登录")
    @PostMapping("/phone-code-login")
    public R phoneCodeLogin(@ApiParam("登录实体类") @RequestBody LoginVO loginVO) {

        if (StringUtils.isEmpty(loginVO.getAccount()) || StringUtils.isEmpty(loginVO.getPassword())) {
            return R.setResult(ResultCodeEnum.MOBILE_REGISTER_ERROR);
        }
        String token = memberService.login(loginVO, UcenterConstant.MOBILE_CODE);
        return R.ok().data("token", token);
    }


    @ResponseBody
    @ApiOperation("账号密码【登录】")
    @PostMapping("/login")
    public R login(@ApiParam("登录实体类") @RequestBody LoginVO loginVO) {

        if (StringUtils.isEmpty(loginVO.getAccount())) {
            return R.setResult(ResultCodeEnum.ACCOUNT_IS_NULL);
        }
        if (FormUtils.isMobile(loginVO.getAccount())) {

            String token = memberService.login(loginVO, UcenterConstant.MOBILE_LOGO);
            return R.ok().data("token", token);
        } else if (FormUtils.isMail(loginVO.getAccount())) {

            String token = memberService.login(loginVO, UcenterConstant.MAIL_LOGO);
            return R.ok().data("token", token);
        } else {

            return R.setResult(ResultCodeEnum.ACCOUNT_ERROR);
        }
    }

    @ResponseBody
    @ApiOperation("账号密码【注册】")
    @PostMapping("/register")
    public R register(@ApiParam("注册实体类") @RequestBody RegisterVO registerVO) {

        if (StringUtils.isEmpty(registerVO.getAccount())) {
            return R.setResult(ResultCodeEnum.ACCOUNT_IS_NULL);
        }

        if (FormUtils.isMobile(registerVO.getAccount())) {

            memberService.register(registerVO, UcenterConstant.MOBILE_LOGO);
            return R.ok().message("注册成功");
        } else if (FormUtils.isMail(registerVO.getAccount())) {

            memberService.register(registerVO, UcenterConstant.MAIL_LOGO);
            return R.ok().message("注册成功");
        } else {

            return R.setResult(ResultCodeEnum.ACCOUNT_ERROR);
        }
    }

    @ResponseBody
    @ApiOperation("根据token获取登录信息")
    @GetMapping("/auth/token-info")
    public R getLoginInfo(HttpServletRequest request) {

        try {
            JwtInfo infoFromJWT = JWTUtils.getInfoFromJWT(request);
            return R.ok().data("userInfo", infoFromJWT);
        }catch (Exception e) {
            log.error("解析用户信息失败," + e.getMessage());
            throw new RException(ResultCodeEnum.TOKEN_ERROR);
        }
    }

    @ResponseBody
    @ApiOperation("根据会员ID查询会员信息")
    @GetMapping("/inner/member-dto/{memberId}")
    public MemberDTO getCourseDTOById(@ApiParam(value = "会员ID", required = true)
                                      @PathVariable("memberId") String memberId) {

        return memberService.getMemberDTOById(memberId);

    }

    @ResponseBody
    @ApiOperation("会员个人资料获取")
    @GetMapping("/info")
    public R info(HttpServletRequest request) {

        JwtInfo infoFromJWT = JWTUtils.getInfoFromJWT(request);
        if (infoFromJWT == null) {
            return R.setResult(ResultCodeEnum.TOKEN_AUTHENTICATION);
        }

        MemberVO info = memberService.getMemberVOById(infoFromJWT.getId());

        return R.ok().data("info", info);
    }

    @ResponseBody
    @ApiOperation("微博登录")
    @GetMapping("/weibo/login")
    public R weiboLogin() {

        String url = "https://api.weibo.com/oauth2/authorize?client_id=" + weiboProperties.getAppKey() +
                "&response_type=code&" +
                "redirect_uri=" + RETURN_URL;
        return R.ok().data("url", url);
    }


    @ApiOperation("微博授权回调地址")
    @GetMapping("/weibo/return")
    public String weiboReturnUrl(HttpServletRequest request) {

        String code = request.getParameter("code");

        if (StringUtils.isBlank(code)) {
            throw new RException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 1. 根据code获取access_token
        String fetchTokenUrl = "https://api.weibo.com/oauth2/access_token?" +
                "client_id=" + weiboProperties.getAppKey() +
                "&client_secret=" + weiboProperties.getAppSecret() +
                "&grant_type=authorization_code" +
                "&redirect_uri=" + RETURN_URL +
                "&code=" + code;

        String tokenJson = HttpUtil.post(fetchTokenUrl, "", 12000);
        System.out.println("tokenJson:" +tokenJson);
        Gson gson = new Gson();
        HashMap accessMap = gson.fromJson(tokenJson, HashMap.class);

        String error = (String) accessMap.get("error");
        String errorCode = (String) accessMap.get("error_code");
        if (error != null || errorCode != null) {
            throw new RException(error, Integer.decode(errorCode));
        }

        System.err.println("error:"+error);
        System.err.println("errorCode:"+errorCode);

        Object accessToken = accessMap.get("access_token");
        Object uid = accessMap.get("uid");

        // 2. 获取用户信息
        String fetchUserInfo = "https://api.weibo.com/2/users/show.json?" +
                "access_token=" + accessToken +
                "&uid=" + uid;
        String userInfoJson = HttpUtil.get(fetchUserInfo, Charset.defaultCharset());

        HashMap infoMap = gson.fromJson(userInfoJson, HashMap.class);

        System.out.println("________________________________");
        Object id = infoMap.get("idstr");
        Object name = infoMap.get("name");
        Object avatarLarge = infoMap.get("avatar_large");

        String result = memberService.addWeiboUser(id, name, avatarLarge);


        return "redirect:" + "http://localhost:3000?token=" + result;
    }

    @ApiOperation("微博取消授权回调地址")
    @GetMapping("/weibo/cancel")
    public R weiboCancelUrl(HttpServletRequest request) {

        request.getParameter("");

        return R.ok().data("returnUrl", "");
    }


}

