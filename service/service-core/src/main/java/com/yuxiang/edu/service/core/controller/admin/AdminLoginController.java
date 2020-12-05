package com.yuxiang.edu.service.core.controller.admin;

import com.yuxiang.edu.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: yuxiang
 * @Date: 2020/11/19 16:16
 */

@Api(description = "管理员登录控制类")
@RestController
@RequestMapping("/admin/core")
public class AdminLoginController {

    @ApiOperation("管理员登录")
    @PostMapping("/login")
    public R login() {

        return R.ok().data("token", "admin.token");
    }

    @ApiOperation("管理员信息")
    @GetMapping("/info")
    public R tokenInfo() {

        return R.ok()
                .data("name", "yuxiang")
                .data("roles", "[admin,user]")
                .data("avatar", "https://video-guli.oss-cn-beijing.aliyuncs.com/teacher/yuxin.png");
    }

    @ApiOperation("管理员退出登录")
    @PostMapping("/logout")
    public R logout(HttpServletRequest request) {

        return R.ok();
    }

}
