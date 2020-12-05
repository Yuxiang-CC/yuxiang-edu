package com.yuxiang.edu.service.ucenter.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: yuxiang
 * @create: 2020-11-15
 **/
@Data
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 81675564181045596L;
    private String account;
    private String password;

}
