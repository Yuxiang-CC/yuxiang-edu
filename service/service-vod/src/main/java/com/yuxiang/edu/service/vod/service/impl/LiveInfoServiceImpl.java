package com.yuxiang.edu.service.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxiang.edu.service.base.constant.VodConstant;
import com.yuxiang.edu.service.vod.entity.LiveInfo;
import com.yuxiang.edu.service.vod.mapper.LiveInfoMapper;
import com.yuxiang.edu.service.vod.service.LiveInfoService;
import com.yuxiang.edu.service.vod.util.LiveUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 直播信息表 服务实现类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-18
 */
@Service
public class LiveInfoServiceImpl extends ServiceImpl<LiveInfoMapper, LiveInfo> implements LiveInfoService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String play(LiveInfo liveInfo) {

        // 1. 将开播信息保存到数据库
        liveInfo.setId("");
        QueryWrapper<LiveInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tid", liveInfo.getTid());
        baseMapper.update(liveInfo, queryWrapper);
        // 2. 获取用户直播间房号
//        QueryWrapper<LiveInfo> query = new QueryWrapper<>();
//        query.select("id","tid");
//        query.eq("tid", liveInfo.getTid());
//        LiveInfo live = baseMapper.selectOne(query);
        // 3. 生成推流、播流地址，并与播主房间号关联存入缓存
        String pushDomain = LiveUrlUtils.generate_push_url(VodConstant.PUSH_DOMAIN,
                VodConstant.PUSH_KEY,
                VodConstant.APP_NAME,
                liveInfo.getTid(),
                VodConstant.EXPIRE_TIME);
        Map<String, Object> pullDomains = LiveUrlUtils.generate_pull_url(VodConstant.PULL_DOMAIN,
                VodConstant.PULL_KEY,
                VodConstant.APP_NAME,
                liveInfo.getTid(),
                VodConstant.EXPIRE_TIME);

        // 存入缓存
        redisTemplate.opsForHash().putAll(VodConstant.LIVE_CACHE_PREFIX + liveInfo.getTid(), pullDomains);
        redisTemplate.opsForHash().put(VodConstant.LIVE_CACHE_PREFIX + liveInfo.getTid(), VodConstant.PUSH, pushDomain);
        redisTemplate.opsForHash().put(VodConstant.LIVE_CACHE_PREFIX + liveInfo.getTid(), VodConstant.INFO, liveInfo);

        // 将直播房间号，按分类存入缓存，以便用于查询
        redisTemplate.opsForZSet().add(VodConstant.LIVE_CATEGORY_PREFIX + liveInfo.getCategoryId(), liveInfo.getTid(), 1 );

        // TODO 开启直播间聊天室

        // 4. 将推流地址返回

        return pushDomain;
    }
}
