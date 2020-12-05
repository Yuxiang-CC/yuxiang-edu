package com.yuxiang.edu.service.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.core.entity.Course;
import com.yuxiang.edu.service.core.entity.Teacher;
import com.yuxiang.edu.service.core.entity.vo.TeacherQueryVO;
import com.yuxiang.edu.service.core.feign.OssFileService;
import com.yuxiang.edu.service.core.mapper.CourseMapper;
import com.yuxiang.edu.service.core.mapper.TeacherMapper;
import com.yuxiang.edu.service.core.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
@Service
@Slf4j
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private OssFileService ossFileService;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public boolean removeAvatarById(String id) {
        // 根据id 获取讲师图像rul
        Teacher teacher = baseMapper.selectById(id);
        if (teacher == null) {
            return false;
        }
        String avatar = teacher.getAvatar();
        if (StringUtils.isNotBlank(avatar)) {
            R r = ossFileService.removeFile(avatar);
            log.error(r.toString());
            return r.getSuccess();
        }
        return false;
    }

    @Override
    public Page<Teacher> selectPage(Page<Teacher> pageParam, TeacherQueryVO teacherQueryVO) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
//        1、排序：按照sort字段升序排序
        queryWrapper.orderByAsc("sort");

//        2、无条件查询
        if (teacherQueryVO == null) {
            return baseMapper.selectPage(pageParam, queryWrapper);
        }
//        3、条件查询
        String name = teacherQueryVO.getName();
        Integer level = teacherQueryVO.getLevel();
        String joinDateBegin = teacherQueryVO.getJoinDateBegin();
        String joinDateEnd = teacherQueryVO.getJoinDateEnd();

        if (StringUtils.isNotBlank(name)) {
            queryWrapper.likeRight("name", name);
        }

        if (level != null) {
            queryWrapper.eq("level", level);
        }

        if (StringUtils.isNotBlank(joinDateBegin)) {
            queryWrapper.ge("join_date", joinDateBegin);
        }

        if (StringUtils.isNotBlank(joinDateEnd)) {
            queryWrapper.le("join_date", joinDateEnd);
        }

        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public List<Map<String, Object>> selectByNameList(String nameKey) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name").likeRight("name", nameKey);
        List<Map<String, Object>> maps = baseMapper.selectMaps(queryWrapper);
        return maps;
    }

    @Cacheable(value = "hot:index", key = "'teacher'")
    @Override
    public List<Teacher> selectHotTeacher() {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sort");
        queryWrapper.last("limit 4");

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Map<String, Object> selectTeacherInfoById(String id) {
        Teacher teacher = baseMapper.selectById(id);

        if (teacher == null) {
            return null;
        }
        // 查询讲师发表的课程
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", id);
        List<Course> courses = courseMapper.selectList(queryWrapper);

        Map<String, Object> maps = new HashMap<>(8);
        maps.put("teacher", teacher);
        maps.put("courseList", courses);

        return maps;
    }
}
