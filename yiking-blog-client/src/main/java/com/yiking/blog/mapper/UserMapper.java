package com.yiking.blog.mapper;

import com.yiking.blog.entities.Role;
import com.yiking.blog.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
   // @Select("  SELECT r.* FROM roles r,roles_user ru WHERE r.`id`=ru.`rid` AND ru.`uid`=#{uid}")
    List<Role> getRolesByUid(Long uid);
   //@Select(" SELECT * FROM user WHERE username=#{username}")
   User loadUserByUsername(@Param("username") String username);

   User getUserById(@Param("id")Long id);

    long reg(User user);

    int updateHeadImg(@Param("headImg")String headImg,@Param("username")String username);

    String getUserBg(@Param("username")String username);

    int updateUserBg(@Param("username")String username,@Param("background") String background);
}
