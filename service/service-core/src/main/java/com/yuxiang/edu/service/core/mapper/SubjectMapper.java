package com.yuxiang.edu.service.core.mapper;

import com.yuxiang.edu.service.core.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuxiang.edu.service.core.entity.vo.SubjectVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
public interface SubjectMapper extends BaseMapper<Subject> {

    /**
     * 获取全部【课程分类】
     * @param parentId
     * @return
     */
    List<SubjectVO> selectNestedSByParentId(@Param("parentId") String parentId);
}
