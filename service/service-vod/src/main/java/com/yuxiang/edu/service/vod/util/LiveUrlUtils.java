package com.yuxiang.edu.service.vod.util;

import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.ExceptionUtils;
import com.yuxiang.edu.service.base.constant.VodConstant;
import com.yuxiang.edu.service.base.exception.RException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: yuxiang
 * @Date: 2020/11/18 21:08
 */
public class LiveUrlUtils {



    /**
     * 计算md5
     * @param param
     * @return
     */
    public static String md5(String param) {
        if(param == null || param.length() == 0) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(param.getBytes());
            byte[] byteArray = md5.digest();

            BigInteger bigInt = new BigInteger(1, byteArray);
            // 参数16表示16进制
            String result = bigInt.toString(16);
            // 不足32位高位补零
            while(result.length() < 32) {
                result = "0" + result;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            ExceptionUtils.getMessage(e);
            throw new RException(ResultCodeEnum.LIVE_PLAY_ERROR);
        }
    }
    /**
     * 生成推流地址
     * @param pushDomain 推流域名
     * @param pushKey 推流域名配置的鉴权Key
     * @param appName 推流AppName
     * @param streamName 推流StreamName
     * @param expireTime 过期时间（单位是秒）
     */
    public static String generate_push_url(String pushDomain,String pushKey,String appName,String streamName,long expireTime) {
        String pushUrl = "";
        //推流域名未开启鉴权功能的情况下
        if(pushKey=="") {
            pushUrl = "rtmp://"+pushDomain+"/"+appName+"/"+streamName;
        }else {
            long timeStamp = System.currentTimeMillis()/1000L + expireTime;
            String rand = UUID.randomUUID().toString().replaceAll("-", "");
            String stringToMd5 = "/"+appName+"/"+streamName+"-"+Long.toString(timeStamp)+"-" + rand + "-0-"+pushKey;
            String authKey = md5(stringToMd5);
            pushUrl = "rtmp://"+pushDomain+"/"+appName+"/"+streamName+"?auth_key="+Long.toString(timeStamp)+"-" + rand + "-0-"+authKey;
        }
        System.out.println("推流地址是： "+pushUrl);
        return pushUrl;
    }
    /**
     * 生成播放地址
     * @param pullDomain 播放域名
     * @param pullKey 播放鉴权Key
     * @param appName 播放appName（同推流appName)
     * @param streamName 播放streamName (同推流streamName）
     * @param expireTime 过期时间（单位是秒
     */
    public static Map<String, Object> generate_pull_url(String pullDomain, String pullKey, String appName, String streamName, long expireTime) {
        String rtmpUrl = ""; //rtmp的拉流地址
        String hlsUrl = "";  //m3u8的拉流地址
        String flvUrl = "";  //flv的拉流地址
        //播放域名未配置鉴权Key的情况下
        if(pullKey == "") {
            rtmpUrl = "rtmp://"+pullDomain+"/"+appName+"/"+streamName;
            hlsUrl = "http://"+pullDomain+"/"+appName+"/"+streamName+".m3u8";
            flvUrl = "http://"+pullDomain+"/"+appName+"/"+streamName+".flv";
        }else {
            long timeStamp = System.currentTimeMillis() / 1000L + expireTime;
            String rand = UUID.randomUUID().toString().replaceAll("-", "");

            String rtmpToMd5 = "/"+appName+"/"+streamName+"-"+Long.toString(timeStamp)+"-"+ rand +"-0-"+pullKey;
            String rtmpAuthKey = md5(rtmpToMd5);
            rtmpUrl = "rtmp://"+pullDomain+"/"+appName+"/"+streamName+"?auth_key="+Long.toString(timeStamp)+"-"+ rand +"-0-"+rtmpAuthKey;

            String hlsToMd5 = "/"+appName+"/"+streamName+".m3u8-"+Long.toString(timeStamp)+"-0-0-"+pullKey;
            String hlsAuthKey = md5(hlsToMd5);
            hlsUrl = "http://"+pullDomain+"/"+appName+"/"+streamName+".m3u8"+"?auth_key="+Long.toString(timeStamp)+"-0-0-"+hlsAuthKey;

            String flvToMd5 = "/"+appName+"/"+streamName+".flv-"+Long.toString(timeStamp)+"-0-0-"+pullKey;
            String flvAuthKey = md5(flvToMd5);
            flvUrl = "http://"+pullDomain+"/"+appName+"/"+streamName+".flv"+"?auth_key="+Long.toString(timeStamp)+"-0-0-"+flvAuthKey;
        }
        System.out.println("RTMP播放地址为： "+rtmpUrl);
        System.out.println("m3u8播放地址为： "+hlsUrl);
        System.out.println("flv播放地址为： "+flvUrl);
        Map<String, Object> domains = new HashMap<>(8);
        domains.put(VodConstant.RMTP, rtmpUrl);
        domains.put(VodConstant.M3U8, hlsUrl);
        domains.put(VodConstant.FLV, flvUrl);

        return domains;
    }
}
