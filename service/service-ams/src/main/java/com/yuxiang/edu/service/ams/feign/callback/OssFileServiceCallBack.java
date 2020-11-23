package com.yuxiang.edu.service.ams.feign.callback;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.ams.feign.OssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: yuxiang
 * @Date: 2020/11/20 10:30
 */
@Component
@Slf4j
public class OssFileServiceCallBack implements OssFileService {
    @Override
    public R test() {
        log.error(this.toString() +"test error");
        return null;
    }

    @Override
    public R removeFile(String url) {
        log.error(this.toString() +" removeFile error");
        return null;
    }
}
