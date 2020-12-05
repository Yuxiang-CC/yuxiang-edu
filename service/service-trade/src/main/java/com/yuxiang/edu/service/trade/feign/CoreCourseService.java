package com.yuxiang.edu.service.trade.feign;

import com.yuxiang.edu.service.base.dto.CourseDTO;
import com.yuxiang.edu.service.trade.feign.callbak.CoreCourseServiceCallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 15:02
 */
@FeignClient(value = "service-core", fallback = CoreCourseServiceCallback.class)
public interface CoreCourseService {

    @GetMapping("/api/core/course/inner/course-dto/{courseId}")
    CourseDTO getCourseDTOById(@PathVariable("courseId") String courseId);
}
