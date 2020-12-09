package com.yuxiang.edu.service.sms.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.ExceptionUtils;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.sms.config.SmsProperties;
import com.yuxiang.edu.service.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author: yuxiang
 * @Date: 2020/11/16 19:23
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsProperties smsProperties;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Override
    public void sendCode(String phone, String sixCode) {

        DefaultProfile profile = DefaultProfile.getProfile(
                smsProperties.getRegionId(),
                smsProperties.getAccessKeyId(),
                smsProperties.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", smsProperties.getRegionId());
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", smsProperties.getSignName());
        request.putQueryParameter("TemplateCode", smsProperties.getTemplateCode());
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + sixCode + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
            throw new RException(ResultCodeEnum.MOBILE_FORMAT_ERROR);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new RException(ResultCodeEnum.UNKNOWN_REASON);
        }

    }

    @Override
    public void sendMail(String mail, String sixCode){
        try{
            MimeMessage msg = javaMailSender.createMimeMessage();
            // 使用true标志表示您需要一个多部分消息，UTF-8设置邮件消息编码
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
            /**
             * 发送者邮箱账号和昵称
             * 当为，雨巷<ren666665@163.com>时，邮件发送昵称为<雨巷>
             * 当为，ren666665@163.com时，邮件发送昵称为<ren666665>
             */
            helper.setFrom("雨巷<ren666665@163.com>");
            helper.setTo("610232665@qq.com");
            helper.setSubject("会员注册");
            // 使用true标志解析为HTML
            helper.setText("<div style=\"background-color:#f7f7f7;padding-top:30px;padding-bottom:30px;\"> \n" +
                    "　<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin: auto;background-color:white;font-family:PingFangSC,&quot;Microsoft Yahei&quot;,&quot;Heiti SC&quot;,sans-serif ;border-radius:8px;overflow:hidden;\">\n" +
                    "<tbody><tr style=\"width:500px;height:100px;background: #58bb85;\">\n" +
                    " <th style=\"width:500px;\"><img src=\"https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/yuxiang.png\" alt=\"雨巷\" width=\"250px\" height=\"100px\"></th>\n" +
                    "</tr>\n" +
                    " <tr style=\"width:500px;height:60px;\"><td><h2 style=\"color:#555555;margin-left:36px;margin-top:34px;\">亲爱的用户 ,</h2></td>\n" +
                    "</tr>\n" +
                    "<tr style=\"width:500px;height:70px;\">\n" +
                    "<td>\n" +
                    "<p style=\"font-size: 14px;line-height: 20px;color: #393939;margin-left:36px;margin-top:14px;margin-right: 36px;\">感谢您注册雨巷学院。您的邮箱验证码是<strong>" + sixCode + "</strong>，验证码将于5分钟后过期。</p>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "<tr style=\"width:500px;height:100px;\">\n" +
                    "<td>\n" +
                    "<p style=\"margin-left:36px;font-size: 14px;line-height: 20px;color: #393939;\">\n" +
                    "雨巷学院教育平台\n" +
                    "</p>\n" +
                    "<a style=\"margin-left:36px;font-size: 14px;color: #4d90fd;display: block;\" href=\"http://www.yuxiangai.cn\">www.yuxiangai.cn</a>\n" +
                    "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "</div>", true);
            // 添加附件
            //FileSystemResource file = new FileSystemResource(new File("d:/yuxiang.png"));
            //helper.addAttachment("人员名单照片", file);

            javaMailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            ExceptionUtils.getMessage(ex);
            throw new RException(ResultCodeEnum.MAIL_SEND_ERROR);
        } catch (MessagingException ex) {
            ExceptionUtils.getMessage(ex);
            throw new RException(ResultCodeEnum.UNKNOWN_REASON);
        }
    }
}
