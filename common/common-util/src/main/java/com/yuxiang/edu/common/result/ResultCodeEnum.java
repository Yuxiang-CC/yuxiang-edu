package com.yuxiang.edu.common.result;

/**
 * @Author: yuxiang
 * @Date: 2020/11/15 12:34
 */
public enum ResultCodeEnum {
    SUCCESS(true, 20000,"成功"),


    UNKNOWN_REASON(false, 40000, "未知错误"),
    ACCOUNT_IS_NULL(false, 40001, "账号为空"),
    ACCOUNT_ERROR(false, 40002, "账号格式错误"),
    DATA_NOT_FOUND(false, 40003, "数据不存在"),

    MOBILE_REGISTER_ERROR(false, 41101, "请填写手机号码"),
    MOBILE_CODE_ERROR(false, 41102, "手机验证码错误"),
    MOBILE_EXIST_ERROR(false, 41103, "该手机号已注册"),
    MOBILE_FORMAT_ERROR(false, 41104, "手机号格式错误"),


    MAIL_REGISTER_ERROR(false, 41201, "请填写邮箱账号"),
    MAIL_CODE_ERROR(false, 41202, "邮箱验证码错误"),
    MAIL_EXIST_ERROR(false, 41203, "该邮箱已注册"),
    MAIL_SEND_ERROR(false, 41204, "邮件发送失败"),


    FILE_UPLOAD_ERROR(false, 41301, "文件上传失败"),

    VIDEO_UPLOAD_ALIYUN_ERROR(false, 41401, "视频上传至阿里云失败"),
    VIDEO_UPLOAD_TOMCAT_ERROR(false, 41402, "视频上传至业务服务器失败"),
    VIDEO_DELETE_ALIYUN_ERROR(false, 41403, "阿里云视频文件删除失败"),
    FETCH_VIDEO_URL_ERROR(false, 41404, "获取播放地址失败"),
    FETCH_VIDEO_PLAYAUTH_ERROR(false, 41405, "获取播放凭证失败"),


    LIVE_PLAY_ERROR(false, 41501, "开启直播间失败，请联系管理员"),


    COURSE_NOT_EXIST(false, 41601, "课程不存在"),

    EXCEL_DATA_IMPORT_ERROR(false, 41701, "Excel表格导入失败"),

    PARAM_ERROR(false, 41801, "参数错误"),

    ALIPAY_PARAME_ERROR(false, 41901, "支付参数错误"),
    ALIPAY_CALL_ERROR(false, 41901, "调用支付错误"),

    TOKEN_ERROR(false, 42101, "token error"),
    TOKEN_EXPIRED(false, 42102, "token 过期"),
    LOGIN_ERROR(false, 42103, "登录失败"),
    TOKEN_AUTHENTICATION(false, 42222, "未登录"),
    TOKEN_USER_INFO_ERROR(false, 42333, "token解析用户信息失败"),


    PASSWORD_ERROR(false, 42102, "账号或密码错误"),


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
