package com.yuxiang.edu.common.result;

/**
 * @Author: yuxiang
 * @Date: 2020/11/15 12:34
 */
public enum ResultCodeEnum {
    SUCCESS(true, 20000,"成功"),


    UNKNOWN_REASON(false, 40000, "未知错误"),


    MOBILE_REGISTER_ERROR(false, 41101, "请填写手机号码"),
    MOBILE_CODE_ERROR(false, 41102, "手机验证码错误"),
    MOBILE_EXIST_ERROR(false, 41103, "该手机号已注册"),
    MOBILE_FORMAT_ERROR(false, 41104, "手机号格式错误"),


    MAIL_REGISTER_ERROR(false, 41201, "请填写邮箱账号"),
    MAIL_CODE_ERROR(false, 41202, "邮箱验证码错误"),
    MAIL_EXIST_ERROR(false, 41203, "该邮箱已注册"),
    MAIL_SEND_ERROR(false, 41204, "邮件发送失败"),


    TOKEN_ERROR(false, 42101, "token error"),
    TOKEN_EXPIRED(false, 42102, "token 过期"),

    PASSWORD_ERROR(false, 42102, "password error")

    ;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
