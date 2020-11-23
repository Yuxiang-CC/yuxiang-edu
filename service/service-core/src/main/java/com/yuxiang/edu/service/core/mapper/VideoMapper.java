package com.yuxiang.edu.service.core.mapper;

import com.yuxiang.edu.service.core.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
@Repository
@Mapper
public interface VideoMapper extends BaseMapper<Video> {

}
