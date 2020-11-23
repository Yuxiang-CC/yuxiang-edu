package com.yuxiang.edu.service.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxiang.edu.service.vod.entity.LiveCategory;
import com.yuxiang.edu.service.vod.entity.vo.LiveCategoryVO;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 直播分类表 服务类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-18
 */
public interface LiveCategoryService extends IService<LiveCategory> {

    /**
     * 获取全部直播分类信息
     * @return
     */
    List<LiveCategory> getAll();

    /**
     * 获取全部直播分类信息【结果嵌套】
     * @return
     */
    List<LiveCategoryVO> getNestedList();

    /**
     * 处理上传的Excel文件流
     * @param inputStream
     */
    void batchImport(InputStream inputStream);
}
