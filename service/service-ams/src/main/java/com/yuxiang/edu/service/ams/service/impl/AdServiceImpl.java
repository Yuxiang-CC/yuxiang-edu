package com.yuxiang.edu.service.ams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.ams.entity.Ad;
import com.yuxiang.edu.service.ams.entity.vo.AdVO;
import com.yuxiang.edu.service.ams.feign.OssFileService;
import com.yuxiang.edu.service.ams.mapper.AdMapper;
import com.yuxiang.edu.service.ams.service.AdService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 广告推荐 服务实现类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-22
 */
@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements AdService {

    @Autowired
    private OssFileService ossFileService;

    /**
     * @CachePut 标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，
     * 而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
     * @param adTypeId
     * @return
     */
    @Cacheable(value = "hot:ad", key = "#adTypeId")
    @Override
    public List<Ad> selectByAdTypeId(String adTypeId) {

        QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort", "id");
        queryWrapper.eq("type_id", adTypeId);

        List<Ad> ads = baseMapper.selectList(queryWrapper);

        return ads;
    }

    @Override
    public boolean removeAdImageById(String id) {
        Ad ad = baseMapper.selectById(id);
        if(ad != null) {
            String imagesUrl = ad.getImageUrl();
            if(StringUtils.isNotBlank(imagesUrl)){
                //删除图片
                R r = ossFileService.removeFile(imagesUrl);
                return r.getSuccess();
            }
        }
        return false;
    }

    @Override
    public IPage<Ad> selectPage(Long page, Long limit) {
        QueryWrapper<AdVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("a.type_id", "a.sort");

        Page<Ad> pageParam = new Page<>(page, limit);

        List<Ad> records = baseMapper.selectPageByQueryWrapper(pageParam, queryWrapper);
        pageParam.setRecords(records);
        return pageParam;
    }
}
