package com.yuxiang.edu.service.ucenter.service;

import com.yuxiang.edu.service.base.dto.MemberDTO;
import com.yuxiang.edu.service.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxiang.edu.service.ucenter.entity.vo.LoginVO;
import com.yuxiang.edu.service.ucenter.entity.vo.MemberVO;
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

    /**
     * 根据Id获取会员信息
     * @param memberId
     * @return
     */
    MemberDTO getMemberDTOById(String memberId);

    /**
     * 获取会员信息
     * @param memberId
     * @return
     */
    MemberVO getMemberVOById(String memberId);

    /**
     * 添加微博登录用户信息
     * @param id 微博用户唯一ID
     * @param name
     * @param avatarLarge
     * @return
     */
    String addWeiboUser(Object id, Object name, Object avatarLarge);
}
