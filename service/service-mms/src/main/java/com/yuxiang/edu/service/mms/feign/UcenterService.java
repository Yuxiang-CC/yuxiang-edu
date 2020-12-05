package com.yuxiang.edu.service.mms.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: yuxiang
 * @Date: 2020/12/1 10:01
 */
@FeignClient(value = "service-ucenter")
public interface UcenterService {

}
