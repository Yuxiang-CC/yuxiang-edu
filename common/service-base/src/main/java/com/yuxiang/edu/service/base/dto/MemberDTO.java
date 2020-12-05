package com.yuxiang.edu.service.base.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Yuxiang
 * @create: 2020-07-02
 **/
@ApiModel("会员信息")
@Data
public class MemberDTO implements Serializable {

    private static final long serialVersionUID = -7978459113797009760L;

    private String id; // 会员Id
    private String mobile; // 会员手机号
    private String nickname; // 会员名称
}
