package com.yuxiang.edu.service.trade.feign.callbak;

import com.yuxiang.edu.service.base.dto.CourseDTO;
import com.yuxiang.edu.service.trade.feign.CoreCourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 15:03
 */
@Component
@Slf4j
public class CoreCourseServiceCallback implements CoreCourseService {

    @Override
    public CourseDTO getCourseDTOById(String courseId) {
        log.info("熔断保护");
        return null;
    }
}
