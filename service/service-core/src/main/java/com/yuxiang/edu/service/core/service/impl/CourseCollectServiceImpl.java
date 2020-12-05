package com.yuxiang.edu.service.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxiang.edu.service.core.entity.CourseCollect;
import com.yuxiang.edu.service.core.entity.vo.CourseCollectVO;
import com.yuxiang.edu.service.core.mapper.CourseCollectMapper;
import com.yuxiang.edu.service.core.mapper.CourseMapper;
import com.yuxiang.edu.service.core.service.CourseCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程收藏 服务实现类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-19
 */
@Service
public class CourseCollectServiceImpl extends ServiceImpl<CourseCollectMapper, CourseCollect> implements CourseCollectService {


    @Autowired
    private CourseMapper courseMapper;


    @Override
    public boolean addCourseCollect(String memberId, String courseId) {

        // 检查课程是否存在

        CourseCollect courseCollect = new CourseCollect();
        courseCollect.setMemberId(memberId);
        courseCollect.setCourseId(courseId);
        boolean result = this.save(courseCollect);

        return result;
    }

    @Override
    public boolean cancelCourseCollect(String memberId, String courseId) {

        QueryWrapper<CourseCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId)
                .eq("member_id", memberId);

        return this.remove(queryWrapper);
    }

    @Override
    public List<CourseCollectVO> getCourseCollectList(String memberId) {

        QueryWrapper<CourseCollect> queryWrapper = new QueryWrapper<CourseCollect>().eq("member_id", memberId);
        List<CourseCollect> courseCollects = baseMapper.selectList(queryWrapper);

        if (courseCollects.size() <= 0) {
            return null;
        }

        List<String> collects = courseCollects.stream()
                .map(collect -> collect.getCourseId()).collect(Collectors.toList());

        List<CourseCollectVO> results = courseMapper.selectCourseCollectVOList(collects);
        return results;
    }
}
