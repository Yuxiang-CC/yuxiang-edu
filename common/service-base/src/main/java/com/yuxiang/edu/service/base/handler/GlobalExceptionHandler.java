package com.yuxiang.edu.service.base.handler;

import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.ExceptionUtils;
import com.yuxiang.edu.service.base.exception.RException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: yuxiang
 * @Date: 2020/11/15 13:14
 */
@ControllerAdvice(basePackages = "com.yuxiang.edu.service.ucenter")
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R allException(Exception e) {
        String message = ExceptionUtils.getMessage(e);
        System.out.println("message:" +message);
        return R.error().message("all-未知异常捕获！");
    }

    @ExceptionHandler(RException.class)
    public R rException(RException e) {

        return R.error().message(e.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public R tokenException() {

        return R.setResult(ResultCodeEnum.TOKEN_EXPIRED);
    }

}
