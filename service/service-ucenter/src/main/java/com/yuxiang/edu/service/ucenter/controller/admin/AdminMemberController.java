package com.yuxiang.edu.service.ucenter.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.ucenter.entity.Member;
import com.yuxiang.edu.service.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: yuxiang
 * @Date: 2020/11/15 15:41
 */
@Api(value = "用户表 管理员控制器")
@RestController
@RequestMapping("/admin/ucenter/member")
public class AdminMemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("获取全部会员")
    @GetMapping("/")
    public R getAll() {

        List<Member> members = memberService.getAllMember();
        return R.ok().data("members", members);
    }

    @ApiOperation("获取一天内注册的用户")
    @GetMapping("/count/{day}")
    public R countMember(@ApiParam(value = "日期")
                        @JsonFormat(pattern = "yyyy-MM-dd")
                         @PathVariable("day") String day) {

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("gmt_create", day);
        Integer count = memberService.count(queryWrapper);
        return R.ok().data("registerNum", count);
    }
}
