package com.yuxiang.edu.service.vod.mapper;

import com.yuxiang.edu.service.vod.entity.LiveInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 直播信息表 Mapper 接口
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-18
 */
@Repository
@Mapper
public interface LiveInfoMapper extends BaseMapper<LiveInfo> {

}
