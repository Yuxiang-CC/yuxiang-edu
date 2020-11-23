package com.yuxiang.edu.service.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuxiang.edu.service.core.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 评论 Mapper 接口
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
@Repository
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
