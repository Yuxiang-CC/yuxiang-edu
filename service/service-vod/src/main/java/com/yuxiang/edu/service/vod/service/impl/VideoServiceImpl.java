package com.yuxiang.edu.service.vod.service.impl;

import cn.hutool.core.util.IdUtil;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.ExceptionUtils;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.vod.config.VodProperties;
import com.yuxiang.edu.service.vod.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yuxiang
 * @Date: 2020/11/17 16:14
 */
@Slf4j
@Service
public class VideoServiceImpl implements VideoService {

    // 点播服务接入区域
    public static final String regionId = "cn-shanghai";

    @Autowired
    private VodProperties vodProperties;

    @Override
    public String uploadVideo(InputStream inputStream, String originalFilename) {

        String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String file = originalFilename.substring(originalFilename.lastIndexOf("."));
        UploadStreamRequest request = new UploadStreamRequest(
                vodProperties.getAccessKeyId(),
                vodProperties.getAccessKeySecret(),
                title, IdUtil.fastSimpleUUID() + file, inputStream);

        /* 封面图片(可选) */
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /* 模板组ID(可选) */
        request.setTemplateGroupId(vodProperties.getTemplateGroupId());
        /* 工作流ID(可选) */
        request.setWorkflowId(vodProperties.getWorkflowId());

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        if (!response.isSuccess()) {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            log.error("error#code#" + ResultCodeEnum.VIDEO_UPLOAD_ALIYUN_ERROR.getCode() + "VideoId=" + response.getVideoId() +
                    "RequestId=" + response.getRequestId() +
                    "ErrorCode=" + response.getCode() +
                    "ErrorMessage=" + response.getMessage());
            throw new RException(ResultCodeEnum.VIDEO_UPLOAD_ALIYUN_ERROR);
        }

        log.info("VideoId=" + response.getVideoId());
        return response.getVideoId();
    }

    @Override
    public List<Map<String, Object>> getVideoUrl(String vid) {

        DefaultProfile profile = DefaultProfile.getProfile(regionId,
                vodProperties.getAccessKeyId(),
                vodProperties.getAccessKeySecret());
        DefaultAcsClient client = new DefaultAcsClient(profile);

        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(vid);

        try {
            GetPlayInfoResponse response = client.getAcsResponse(request);

            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            List<Map<String, Object>> videos = new ArrayList<>();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.Definition=" + playInfo.getDefinition() + " PlayInfo.PlayURL=" + playInfo.getPlayURL() + "\n");
                Map<String, Object> playInfoMap = new HashMap<>();
                playInfoMap.put("Definition", playInfo.getDefinition());
                playInfoMap.put("PlayURL", playInfo.getPlayURL());
                videos.add(playInfoMap);
            }

            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
            System.out.print("RequestId = " + response.getRequestId() + "\n");

            return videos;
        } catch (Exception e) {
            ExceptionUtils.getMessage(e);
            throw new RException(ResultCodeEnum.FETCH_VIDEO_URL_ERROR);
        } finally {
            client.shutdown();
        }
    }

    @Override
    public String getVideoPlayAuth(String vid) {

        DefaultProfile profile = DefaultProfile.getProfile(regionId,
                vodProperties.getAccessKeyId(),
                vodProperties.getAccessKeySecret());
        DefaultAcsClient client = new DefaultAcsClient(profile);

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(vid);

        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = client.getAcsResponse(request);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta() + "\n");
        } catch (Exception e) {
            ExceptionUtils.getMessage(e);
            throw new RException(ResultCodeEnum.FETCH_VIDEO_PLAYAUTH_ERROR);
        } finally {
            client.shutdown();
        }

        return response.getPlayAuth();
    }

    @Override
    public void removeVideo(String vid) {

        doRemove(vid);
    }

    @Override
    public void removeVideo(List<String> vodIds) {
        StringBuffer vids = new StringBuffer();
        vodIds.forEach(i -> vids.append(i + ","));
        System.out.println(vids.substring(0, vids.length() - 1));

        doRemove(vids.substring(0, vids.length() - 1));
    }

    private void doRemove(final String vids) {

        DefaultProfile profile = DefaultProfile.getProfile(regionId,
                vodProperties.getAccessKeyId(),
                vodProperties.getAccessKeySecret());
        DefaultAcsClient client = new DefaultAcsClient(profile);

        DeleteVideoResponse response = new DeleteVideoResponse();

        try {
            DeleteVideoRequest request = new DeleteVideoRequest();

            /**
             * 支持传入多个视频ID，多个用逗号分隔
             * request.setVideoIds("videoID1,videoID2");
             */
            request.setVideoIds(vids);

            response = client.getAcsResponse(request);
        } catch (Exception e) {
            log.error("error#code#" + response.getRequestId());
            throw new RException(ResultCodeEnum.VIDEO_DELETE_ALIYUN_ERROR);
        } finally {
            client.shutdown();
        }

        log.info("info#code#" + response.getRequestId());

    }
}
