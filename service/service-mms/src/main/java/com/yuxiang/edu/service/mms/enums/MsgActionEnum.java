package com.yuxiang.edu.service.mms.enums;

/**
 * @Author: yuxiang
 * @Date: 2020/11/28 10:43
 */
public enum MsgActionEnum {

    CONNECT(1, "第一次（或重连）初始化连接"),
    CHAT(2, "聊天消息"),
    KEEPALIVE(3, "客户端保持心跳"),
    QUIT(4, "退出直播间");


    MsgActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public final Integer type;
    public final String content;
}
