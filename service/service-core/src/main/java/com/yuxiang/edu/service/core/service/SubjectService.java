package com.yuxiang.edu.service.core.service;

import com.yuxiang.edu.service.core.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxiang.edu.service.core.entity.vo.SubjectVO;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
public interface SubjectService extends IService<Subject> {

    /**
     * 批量导入
     * @param inputStream
     */
    void batchImport(InputStream inputStream);

    /**
     * 查询全部课程信息
     * @return
     */
    List<SubjectVO> nestedList();
}
