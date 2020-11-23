package com.yuxiang.edu.service.ams.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuxiang.edu.service.ams.entity.Ad;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuxiang.edu.service.ams.entity.vo.AdVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 广告推荐 Mapper 接口
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-22
 */
public interface AdMapper extends BaseMapper<Ad> {

    /**
     * 分页查询广告信息
     * @param pageParam
     * @param queryWrapper
     * @return
     */
    List<Ad> selectPageByQueryWrapper(Page<Ad> pageParam,@Param(Constants.WRAPPER) QueryWrapper<AdVO> queryWrapper);
}
