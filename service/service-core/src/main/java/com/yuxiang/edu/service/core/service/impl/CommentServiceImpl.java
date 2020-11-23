package com.yuxiang.edu.service.core.service.impl;

import com.yuxiang.edu.service.core.entity.Comment;
import com.yuxiang.edu.service.core.mapper.CommentMapper;
import com.yuxiang.edu.service.core.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
