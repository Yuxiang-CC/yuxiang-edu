package com.yuxiang.edu.service.ucenter.mapper;

import com.yuxiang.edu.service.base.dto.MemberDTO;
import com.yuxiang.edu.service.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuxiang.edu.service.ucenter.entity.vo.MemberVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-15
 */
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 根据ID获取会员信息
     * @param memberId
     * @return
     */
    MemberDTO selectMemberDTOById(@Param("memberId") String memberId);

    /**
     * 查询会员信息
     * @param memberId
     * @return
     */
    MemberVO selectMemberVOById(String memberId);
}
