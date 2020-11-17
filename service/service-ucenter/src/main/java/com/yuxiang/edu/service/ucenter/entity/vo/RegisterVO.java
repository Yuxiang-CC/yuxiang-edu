package com.yuxiang.edu.service.ucenter.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: yuxiang
 * @create: 2020-11-15
 **/
@Data
public class RegisterVO implements Serializable {

    private static final long serialVersionUID = 3966986395282320372L;
    private String nickName;
    private String mobile;
    private String mail;
    private String password;
    private String code;

}
