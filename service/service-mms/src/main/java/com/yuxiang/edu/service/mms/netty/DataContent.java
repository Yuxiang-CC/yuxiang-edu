package com.yuxiang.edu.service.mms.netty;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: yuxiang
 * @Date: 2020/11/27 17:04
 */
@Data
public class DataContent implements Serializable {

    /**
     * 动作类型
     */
    private Integer action;

    /**
     * 消息内容
     */
    private ChatMessage chatMessage;

    /**
     * 扩展自动
     */
    private String extend;
}
