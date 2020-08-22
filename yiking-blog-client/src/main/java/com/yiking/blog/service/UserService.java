package com.yiking.blog.service;

import com.yiking.blog.entities.Role;
import com.yiking.blog.entities.User;
import com.yiking.blog.mapper.RoleMapper;
import com.yiking.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper rolesMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    public int reg(User user) {
        User loadUserByUsername = userMapper.loadUserByUsername(user.getUsername());
        if (loadUserByUsername != null) {
            return 1;
        }
        //插入用户,插入之前先对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setNickname(user.getUsername());
        user.setUserface("yiking");
        user.setRegTime(new Timestamp(System.currentTimeMillis()));
        user.setEnabled(true);//用户可用
        long result = userMapper.reg(user);
        //配置用户的角色，默认都是普通用户
        String[] roles = new String[]{"2"};
        int i = rolesMapper.addRoles(roles, user.getId());
        boolean b = i == roles.length && result == 1;
        if (b) {
            return 0;
        } else {
            return 2;
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // System.out.println(username);
        User user = userMapper.loadUserByUsername(username);
        //System.out.println(user.getPassword()+"-->"+user.getUsername());

        if (user == null) {
            //避免返回null，这里返回一个不含有任何值的User对象，在后期的密码比对过程中一样会验证失败
            return new User();
        }
        //查询用户的角色信息，并返回存入user中
        List<Role> roles = rolesMapper.getRolesByUid(user.getId());
        user.setRoles(roles);
//        for (Role ss:user.getRoles()) {
//            System.out.println(ss.getName());
//        }
        return user;
    }
    public User getUserById(Long id){
        return userMapper.getUserById(id);
    }
    public int updateUserFace(String headImg,String username){
        int res = userMapper.updateHeadImg(headImg, username);
        return res;
    }
    public User getUserByName(String username){
        return userMapper.loadUserByUsername(username);
    }

    public  String getUserBg(String username){
        return userMapper.getUserBg(username);
    }
    public  int updateUserBg(String username, String background){
        return userMapper.updateUserBg(username,background);
    }
}
