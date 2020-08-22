package com.yiking.blog.mapper;

import com.yiking.blog.entities.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {
    //@Select(" SELECT r.* FROM roles r,roles_user ru WHERE r.`id`=ru.`rid` AND ru.`uid`=#{uid}")
    List<Role> getRolesByUid(Long id);
    int addRoles(@Param("roles") String[] roles, @Param("uid") Long uid);
}
