package com.yuxiang.edu.service.vod.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yuxiang.edu.service.vod.entity.LiveCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 直播分类表 Mapper 接口
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-18
 */
@Repository
@Mapper
public interface LiveCategoryMapper extends BaseMapper<LiveCategory> {

    /**
     * 根据查询条件获取直播分类信息【仅ID字段】
     * @param queryWrapper
     * @return
     */
    LiveCategory selectId(@Param(Constants.WRAPPER) QueryWrapper<LiveCategory> queryWrapper);
}
