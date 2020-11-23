package com.yuxiang.edu.service.vod.service;

import com.yuxiang.edu.service.vod.entity.LiveInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 直播信息表 服务类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-18
 */
public interface LiveInfoService extends IService<LiveInfo> {

    /**
     * 开播
     * @param liveInfo
     * @return
     */
    String play(LiveInfo liveInfo);
}
