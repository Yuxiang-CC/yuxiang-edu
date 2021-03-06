package com.yuxiang.edu.service.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.EncryptionUtils;
import com.yuxiang.edu.common.util.JWTUtils;
import com.yuxiang.edu.common.util.NicknameUtils;
import com.yuxiang.edu.service.base.constant.UcenterConstant;
import com.yuxiang.edu.service.base.dto.MemberDTO;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.ucenter.entity.Member;
import com.yuxiang.edu.service.ucenter.entity.vo.LoginVO;
import com.yuxiang.edu.service.ucenter.entity.vo.MemberVO;
import com.yuxiang.edu.service.ucenter.entity.vo.RegisterVO;
import com.yuxiang.edu.service.ucenter.mapper.MemberMapper;
import com.yuxiang.edu.service.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-15
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<Member> getAllMember() {
        return baseMapper.selectList(null);
    }


    @Override
    public void register(RegisterVO registerVO, int logo) {

        switch (logo) {
            case UcenterConstant.MOBILE_LOGO:
                System.out.println("手机注册");
                // 根据手机号从数据库查询登录，失败：抛异常，成功：生成Token并返回
                // 1.查询该手机是否已经注册过
                QueryWrapper<Member> mobileQuery = new QueryWrapper<>();
                mobileQuery.eq("mobile", registerVO.getAccount());
                Member mobileMember = baseMapper.selectOne(mobileQuery);
                if (mobileMember == null) {
                    // 2.没注册过，则验证验证码是否正确，将会员信息保存起来
                    // 从缓存中获取验证码
                    String code = (String)redisTemplate.opsForValue()
                            .get(UcenterConstant.REGISTRY_PREFIX_KEY + registerVO.getAccount());
                    if (registerVO.getCode().equals(code)) {
                        mobileMember = new Member();
                        mobileMember.setMobile(registerVO.getAccount());
                        mobileMember.setNickname(registerVO.getNickName());
                        mobileMember.setPassword(EncryptionUtils.encodePassword(registerVO.getPassword()));
                        mobileMember.setAvatar(UcenterConstant.USER_AVATAR);
                        baseMapper.insert(mobileMember);
                        return;
                    }
                    throw new RException(ResultCodeEnum.MOBILE_CODE_ERROR);

                } else {
                    // 否侧：抛出异常
                    throw new RException(ResultCodeEnum.MOBILE_EXIST_ERROR);
                }

            case UcenterConstant.MAIL_LOGO:
                System.out.println("邮箱注册");
                // 根据邮箱账号从数据库查询登录，失败：抛异常，成功：生成Token并返回
                // 1.查询该手机是否已经注册过
                QueryWrapper<Member> mailQuery = new QueryWrapper<>();
                mailQuery.eq("mail", registerVO.getAccount());
                Member mailMember = baseMapper.selectOne(mailQuery);
                if (true) {
                    // 2.没注册过，则将会员信息保存起来
                    // 从缓存中获取验证码
                    String code = (String)redisTemplate.opsForValue()
                            .get(UcenterConstant.REGISTRY_PREFIX_KEY + registerVO.getAccount());
                    if (registerVO.getCode().equals(code)) {
                        mailMember = new Member();
                        mailMember.setMail(registerVO.getAccount());
                        mailMember.setNickname(registerVO.getNickName());
                        mailMember.setPassword(EncryptionUtils.encodePassword(registerVO.getPassword()));
                        mailMember.setAvatar(UcenterConstant.USER_AVATAR);
                        baseMapper.insert(mailMember);
                        return;
                    }

                    throw new RException(ResultCodeEnum.MAIL_CODE_ERROR);
                } else {
                    // 否侧：抛出异常
                    throw new RException(ResultCodeEnum.MAIL_EXIST_ERROR);
                }

            default:
                System.out.println("注册中未知错误");
                throw new RException(ResultCodeEnum.UNKNOWN_REASON);
        }

    }

    @Override
    public String login(LoginVO loginVO, int logo) {

        switch (logo) {
            case UcenterConstant.MOBILE_LOGO:
                System.out.println("手机密码登录");
                // 根据手机号从数据库查询登录，
                QueryWrapper<Member> mobileQuery = new QueryWrapper<>();
                mobileQuery.eq("mobile", loginVO.getAccount());
                mobileQuery.eq("password", EncryptionUtils.encodePassword(loginVO.getPassword()));
                Member mobileMember = baseMapper.selectOne(mobileQuery);
                if (mobileMember == null) {
                    // 失败：抛异常，
                    throw new RException(ResultCodeEnum.PASSWORD_ERROR);
                } else {
                    // 成功：生成Token并返回
                    return generateToken(mobileMember);
                }

            case UcenterConstant.MAIL_LOGO:
                 System.out.println("邮箱密码登录");
                // 根据邮箱从数据库查询登录
                QueryWrapper<Member> mailQuery = new QueryWrapper<>();
                mailQuery.eq("mail", loginVO.getAccount());
                mailQuery.eq("password", EncryptionUtils.encodePassword(loginVO.getPassword()));

                Member mailMember = baseMapper.selectOne(mailQuery);
                if (mailMember == null) {
                    // 失败：抛异常，
                    throw new RException(ResultCodeEnum.PASSWORD_ERROR);
                } else {
                    // 成功：生成Token并返回
                    return generateToken(mailMember);
                }

            case UcenterConstant.MOBILE_CODE:
                System.out.println("手机验证码登录");
                String code = (String)redisTemplate.opsForValue().get(UcenterConstant.LOGIN_PREFIX_KEY + loginVO.getAccount());
                if (loginVO.getPassword().equals(code)) {
                    QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("mobile", loginVO.getAccount());
                    Member member = baseMapper.selectOne(queryWrapper);
                    if (member == null) {
                        // 如果为空则表示用户是第一次登录注册，则将用户信息保存
                        member = new Member();
                        // 自动生成昵称、密码、头像 【密码为手机号】
                        member.setMobile(loginVO.getAccount());
                        member.setNickname(NicknameUtils.getNickname());
                        member.setPassword(EncryptionUtils.encodePassword(loginVO.getAccount()));
                        member.setAvatar(UcenterConstant.USER_AVATAR);
                        baseMapper.insert(member);
                        return generateToken(member);
                    } else {
                        return generateToken(member);
                    }
                }
            default:
                throw new RException(ResultCodeEnum.UNKNOWN_REASON);
        }
    }

    @Override
    public MemberDTO getMemberDTOById(String memberId) {

        return baseMapper.selectMemberDTOById(memberId);
    }

    @Override
    public MemberVO getMemberVOById(String memberId) {

        return baseMapper.selectMemberVOById(memberId);
    }

    @Override
    public String addWeiboUser(Object id, Object name, Object avatarLarge) {

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wb_openid", (String) id);
        Member member = baseMapper.selectOne(queryWrapper);
        // 如果之前为登录过，则直接保存否则更新用户关键数据
        if (member == null) {
            member = new Member();
            member.setWbOpenid((String)id);
            member.setNickname((String)name);
            member.setAvatar((String) avatarLarge);
            int result = baseMapper.insert(member);
            if (result >= 0) {
                // 生成Token返回
                return generateToken(member);
            }
        } else {
            member.setNickname((String)name);
            member.setAvatar((String) avatarLarge);
            baseMapper.update(member, queryWrapper);
        }

        // 生成Token返回
        return generateToken(member);
    }

    private boolean checkCode() {

        return true;
    }

    private String generateToken(Member member) {
        String jwt = JWTUtils.genJwt(
                member.getId(),
                member.getNickname(),
                member.getAvatar());
        System.out.println(jwt);
        return jwt;
    }
}
