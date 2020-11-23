package com.yuxiang.edu.service.core.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuxiang.edu.service.core.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxiang.edu.service.core.entity.vo.TeacherQueryVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 根据讲师ID删除讲师头像
     * @param id
     * @return
     */
    boolean removeAvatarById(String id);

    /**
     * 分页查询讲师
     * @param pageParam
     * @param teacherQueryVO
     * @return
     */
    Page<Teacher> selectPage(Page<Teacher> pageParam, TeacherQueryVO teacherQueryVO);

    /**
     * 根据讲师名字查询用户
     * @param nameKey
     * @return
     */
    List<Map<String, Object>> selectByNameList(String nameKey);

    /**
     * 查询热门讲师
     * @return
     */
    List<Teacher> selectHotTeacher();
}
