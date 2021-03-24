package com.yuxiang.edu.service.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuxiang.edu.service.acl.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Repository
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
