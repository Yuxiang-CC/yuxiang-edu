package com.yuxiang.edu.service.oss.service;

import java.io.InputStream;

/**
 * @Author: yuxiang
 * @Date: 2020/11/17 13:51
 */
public interface FileService {

    /**
     * 删除文件
     * @param url
     */
    void removeFile(String url);

    /**
     * 上传文件
     * @param inputStream
     * @param module
     * @param originalFilename
     * @return
     */
    String upload(InputStream inputStream, String module, String originalFilename);
}
