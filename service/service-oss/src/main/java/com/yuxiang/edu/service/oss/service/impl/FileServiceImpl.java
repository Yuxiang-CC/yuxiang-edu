package com.yuxiang.edu.service.oss.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.yuxiang.edu.service.oss.config.OssProperties;
import com.yuxiang.edu.service.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @Author: yuxiang
 * @Date: 2020/11/17 13:51
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private OssProperties ossProperties;


    @Override
    public String upload(InputStream inputStream, String model, String originalFileName) {
        String endpoint = ossProperties.getEndpoint();
        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeySecret = ossProperties.getAccessKeySecret();
        String bucketName = ossProperties.getBucketName();

        // 创建OSSClient对象
        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 判断BucketName是否存在
//        if (!oss.doesBucketExist(bucketName)) {
//            oss.createBucket(bucketName);
//            // 设置为公共读
//            oss.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
//        }

        /**
         * 构建bucketName  ==> teacher/2020/05/21/uuid.jpg 讲师头像
         * 构建bucketName  ==> cover/2020/05/21/uuid.jpg 课程封面
         */

        String folder = new DateTime().toString("yyyy/MM/dd/");
        String uuid = IdUtil.simpleUUID();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String key = model + "/" + folder + uuid + fileExtension;

        // 上传文件流
        PutObjectResult putObjectResult = oss.putObject(bucketName, key, inputStream);

        // 关闭连接
        oss.shutdown();

        System.out.println("上传成功:" + putObjectResult.getETag());
        // 返回url http://oss-cn-beijing.aliyuncs.com/teacher/default.jpg
        return "https://" + bucketName + "." + endpoint + "/" + key;
    }

    @Override
    public void removeFile(String url) {

        String endpoint = ossProperties.getEndpoint();
        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeySecret = ossProperties.getAccessKeySecret();
        String bucketName = ossProperties.getBucketName();

        // 创建OSSClient对象
        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 截取删除文件
        String host = "https://" + bucketName + "." + endpoint + "/";
        String objectName = url.substring(host.length() + 1);
        System.out.println("文件名：" + objectName);
        oss.deleteObject(bucketName, objectName);

        // 关闭连接
        oss.shutdown();

    }
}
