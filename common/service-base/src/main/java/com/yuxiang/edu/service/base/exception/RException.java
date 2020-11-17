package com.yuxiang.edu.service.base.exception;

import com.yuxiang.edu.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * @Author: yuxiang
 * @Date: 2020/11/15 14:00
 */
@Data
public class RException extends RuntimeException {

    private Integer code;

    public RException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public RException(ResultCodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
    }

}
