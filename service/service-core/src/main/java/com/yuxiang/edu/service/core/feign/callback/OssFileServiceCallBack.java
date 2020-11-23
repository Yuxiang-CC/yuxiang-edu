package com.yuxiang.edu.service.core.feign.callback;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.core.feign.OssFileService;

/**
 * @Author: yuxiang
 * @Date: 2020/11/20 10:30
 */
public class OssFileServiceCallBack implements OssFileService {
    @Override
    public R test() {
        return null;
    }

    @Override
    public R removeFile(String url) {
        return null;
    }
}
