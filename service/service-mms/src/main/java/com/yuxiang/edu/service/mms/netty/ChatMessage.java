package com.yuxiang.edu.service.mms.netty;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: yuxiang
 * @Date: 2020/11/27 17:00
 */
@Data
public class ChatMessage implements Serializable {

    /**
     * 用户ID
     */
    private String memberId;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 直播间ID
     */
    private String liveId;

    /**
     * 消息内容
     */
    private String message;

    /**
     *
     */
}
