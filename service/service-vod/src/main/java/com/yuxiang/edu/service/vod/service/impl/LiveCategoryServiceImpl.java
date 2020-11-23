package com.yuxiang.edu.service.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxiang.edu.service.base.constant.VodConstant;
import com.yuxiang.edu.service.vod.entity.LiveCategory;
import com.yuxiang.edu.service.vod.entity.excel.ExcelLiveCategoryData;
import com.yuxiang.edu.service.vod.entity.vo.LiveCategoryVO;
import com.yuxiang.edu.service.vod.listener.ExcelLiveCategoryListener;
import com.yuxiang.edu.service.vod.mapper.LiveCategoryMapper;
import com.yuxiang.edu.service.vod.service.LiveCategoryService;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 直播分类表 服务实现类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-18
 */
@Service
public class LiveCategoryServiceImpl extends ServiceImpl<LiveCategoryMapper, LiveCategory> implements LiveCategoryService {



    @Override
    public List<LiveCategory> getAll() {
        List<LiveCategory> liveCategories = baseMapper.selectList(null);

        return liveCategories;
    }

    @Override
    public List<LiveCategoryVO> getNestedList() {
        List<LiveCategory> liveCategories = baseMapper.selectList(null);
        List<LiveCategoryVO> liveCategoryVOS = new ArrayList<>(16);
        // 分类Id父类
        liveCategories.stream().forEach(liveCategory -> {
            if (liveCategory.getParentId() != null && VodConstant.LIVA_CATEGORY_ONE_LEVEL.equals(liveCategory.getParentId())) {
                LiveCategoryVO liveVO = new LiveCategoryVO();
                liveVO.setId(liveCategory.getId());
                liveVO.setName(liveCategory.getName());
                liveVO.setParentId(liveCategory.getParentId());
                liveCategoryVOS.add(liveVO);
            } else {
                liveCategoryVOS.stream().forEach(categoryVO -> {
                    if (categoryVO.getId().equals(liveCategory.getParentId())) {
                        LiveCategoryVO liveVO = new LiveCategoryVO();
                        liveVO.setId(liveCategory.getId());
                        liveVO.setName(liveCategory.getName());
                        liveVO.setParentId(liveCategory.getParentId());
                        List<LiveCategoryVO> children = categoryVO.getChildren();
                        if (children == null) {
                            List<LiveCategoryVO> c = new ArrayList<>();
                            c.add(liveVO);
                            categoryVO.setChildren(c);
                        } else {
                            children.add(liveVO);
                        }

                    }
                });
            }
        });

        /*Iterator<LiveCategoryVO> iterator = liveCategoryVOS.iterator();
        while (iterator.hasNext()) {
            LiveCategoryVO next = iterator.next();
            List<LiveCategoryVO> childer = new ArrayList<>();
            liveCategories.stream().forEach(category -> {
                System.out.println(next.getId().equals(category.getParentId()));
                if (category.getParentId() != null && next.getId().equals(category.getParentId())) {
                    LiveCategoryVO liveVO = new LiveCategoryVO();
                    liveVO.setId(category.getId());
                    liveVO.setName(category.getName());
                    liveVO.setParentId(category.getParentId());
                    childer.add(liveVO);
                }
            });
            next.setChildren(childer);
        }*/


        return liveCategoryVOS;
    }

    @Override
    public void batchImport(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelLiveCategoryData.class, new ExcelLiveCategoryListener(baseMapper))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet().doRead();
    }
}
