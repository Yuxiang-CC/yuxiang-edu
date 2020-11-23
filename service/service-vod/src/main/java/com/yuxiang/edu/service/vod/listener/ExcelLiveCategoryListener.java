package com.yuxiang.edu.service.vod.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxiang.edu.service.vod.entity.LiveCategory;
import com.yuxiang.edu.service.vod.entity.excel.ExcelLiveCategoryData;
import com.yuxiang.edu.service.vod.mapper.LiveCategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: yuxiang
 * @Date: 2020/11/23 11:14
 */
@Slf4j
public class ExcelLiveCategoryListener extends AnalysisEventListener<ExcelLiveCategoryData> {


    private LiveCategoryMapper liveCategoryMapper;

    public ExcelLiveCategoryListener(LiveCategoryMapper liveCategoryMapper) {
        this.liveCategoryMapper = liveCategoryMapper;
    }

    @Override
    public void invoke(ExcelLiveCategoryData data, AnalysisContext context) {
        log.info("解析到一条数据,{}", data);

        // 处理读取出来的数据
        // 一级标题
        String levelOneTitle = data.getLevelOneTitle();
        // 二级标题
        String levelTwoTitle = data.getLevelTwoTitle();

        String parentId = "";
        if (StringUtils.isNotBlank(levelOneTitle)) {
            LiveCategory liveCategory = getLiveCategoryOneTitle(levelOneTitle);
            if (liveCategory == null) {
                LiveCategory category = new LiveCategory();
                category.setName(levelOneTitle);
                category.setParentId("0");
                liveCategoryMapper.insert(category);
                parentId = category.getId();
            } else {
                parentId = liveCategory.getId();
            }
        }

        if (StringUtils.isNotBlank(levelTwoTitle)) {
            LiveCategory liveCategory = getLiveCategoryOneWithTwoTitle(levelTwoTitle, parentId);
            if (liveCategory == null) {
                LiveCategory category = new LiveCategory();
                category.setName(levelTwoTitle);
                category.setParentId(parentId);
                liveCategoryMapper.insert(category);
            }
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("直播分类列表模板解析完成！");
    }


    /**
     * 根据一级分类的名称查询该一级分类是否存在
     * @param name 一级分类名称
     * @return
     */
    private LiveCategory getLiveCategoryOneTitle(String name) {
        QueryWrapper<LiveCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        // 一级分类
        queryWrapper.eq("parent_id", "0");

        return liveCategoryMapper.selectId(queryWrapper);
    }

    /**
     * 根据一级分类的名称和父类名称查询该二级分类是否存在
     * @param name 二级分类名称
     * @param parentId 一级分类名称
     * @return
     */
    private LiveCategory getLiveCategoryOneWithTwoTitle(String name, String parentId) {

        QueryWrapper<LiveCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        queryWrapper.eq("parent_id", parentId);

        return liveCategoryMapper.selectId(queryWrapper);
    }
}
