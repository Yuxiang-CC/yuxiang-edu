package com.yuxiang.edu.service.ucenter.service;

import com.yuxiang.edu.service.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxiang.edu.service.ucenter.entity.vo.LoginVO;
import com.yuxiang.edu.service.ucenter.entity.vo.RegisterVO;

import java.util.List;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-15
 */
public interface MemberService extends IService<Member> {

    /**
     * 获取全部会员
     * @return
     */
    List<Member> getAllMember();

    /**
     * 会员注册
     * @param registerVO
     * @param logo
     */
    void register(RegisterVO registerVO, int logo);

    /**
     * 会员登录
     * @param loginVO
     * @param logo
     * @return
     */
    String login(LoginVO loginVO, int logo);

}
