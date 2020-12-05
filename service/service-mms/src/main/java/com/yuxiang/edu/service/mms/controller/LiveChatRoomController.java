package com.yuxiang.edu.service.mms.controller;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.base.constant.VodConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: yuxiang
 * @Date: 2020/11/28 16:01
 */
@Api(description = "直播间人员信息")
@RestController
@RequestMapping("/api/mms/chatroom")
public class LiveChatRoomController {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @ApiOperation("获取直播间聊天室用户信息")
    @GetMapping("/auth/member/{liveId}")
    public R getMember(@PathVariable("liveId") String liveId, HttpServletRequest request) {
        if (StringUtils.isBlank(liveId)) {
            return R.error().message("请输入正确直播间房号");
        }
        // 根据用户ID查询用户信息
        ScanOptions scanOptions = ScanOptions.scanOptions().build();
        List<Object> list = new ArrayList<>(16);
        Cursor<Map.Entry<Object, Object>> scan = redisTemplate.opsForHash().scan(VodConstant.LIVE_MEMBERS_INFO + liveId, scanOptions);
        while (scan.hasNext()) {
            Map.Entry<Object, Object> next = scan.next();
//            System.out.println("field:" + next.getKey() +"\t value:" +next.getValue());
            list.add(next.getValue());
        }

        return R.ok().data("items", list);
    }
}
