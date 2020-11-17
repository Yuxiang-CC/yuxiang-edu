package com.yuxiang.edu.service.sms.service;

/**
 * @Author: yuxiang
 * @Date: 2020/11/16 19:23
 */
public interface SmsService {

    /**
     * 发送手机验证码
     * @param phone
     * @param sixCode
     */
    void sendCode(String phone, String sixCode);

    /**
     * 发送邮箱验证码
     * @param mail
     * @param sixCode
     */
    void sendMail(String mail, String sixCode);
}
