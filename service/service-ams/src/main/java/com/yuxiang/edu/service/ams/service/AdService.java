package com.yuxiang.edu.service.ams.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxiang.edu.service.ams.entity.Ad;

import java.util.List;

/**
 * <p>
 * 广告推荐 服务类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-22
 */
public interface AdService extends IService<Ad> {

    /**
     * 根据广告类型查询广告
     * @param adTypeId
     * @return
     */
    List<Ad> selectByAdTypeId(String adTypeId);

    /**
     * 根据广告ID删除广告图片
     * @param id
     * @return
     */
    boolean removeAdImageById(String id);

    /**
     * 分页查询广告列表
     * @param page
     * @param limit
     * @return
     */
    IPage<Ad> selectPage(Long page, Long limit);
}
