package com.yuxiang.edu.service.mms.netty;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: yuxiang
 * @Date: 2020/11/28 15:26
 */
public class MemberChannelRelation {

    private static Map<String, ConcurrentHashMap<String, Channel>> LIVE = new ConcurrentHashMap<>(16);

    /**
     * 类别分类
     * @param liveId 直播间ID
     * @param map 直播ID对应的人员以及Channel
     */
    public static void put(String liveId, ConcurrentHashMap<String, Channel> map) {
        LIVE.put(liveId, map);
    }

    /**
     * 根据直播间ID获取起直播间成员的Channel
     * @param liveId
     * @return
     */
    public static ConcurrentHashMap<String, Channel> get(String liveId) {

        return LIVE.get(liveId);
    }

    /**
     * 是否有该直播间信息
     * @param liveId
     * @return
     */
    public static boolean contains(String liveId) {
        return LIVE.containsKey(liveId);
    }


}
