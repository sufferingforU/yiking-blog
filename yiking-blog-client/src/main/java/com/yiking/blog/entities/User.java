package com.yiking.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户实体")
public class User implements UserDetails, Serializable {
    @ApiModelProperty(value = "用户id", position =1)
    private Long id;
    @ApiModelProperty(value = "用户名", position =2)
    @Length(min = 4,max =18,message = "用户名长度需在4-18之间")
    private String username;
    @Length(min = 6,max =18,message = "密码长度需在6-18之间")
    @ApiModelProperty(value = "用户登陆密码", position =3)
    private String password;
    @ApiModelProperty(value = "用户昵称", position =4)
    private String nickname;
    @ApiModelProperty(value = "用户是否被禁用", position =5)
    private boolean enabled;
    @ApiModelProperty(value = "用户拥有角色", position =6)
    private List<Role> roles;
    @Email(message = "邮箱格式错误")
    @ApiModelProperty(value = "用户邮箱", position =7)
    private String email;
    @ApiModelProperty(value = "用户头像", position =8)
    private String userface;
    @ApiModelProperty(value = "用户注册时间", position =9)
    private Timestamp regTime;
    @ApiModelProperty(value = "用户头像", position =10)
    private String headImg;
    @ApiModelProperty(value = "用户背景", position =11)
    private String background;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public Timestamp getRegTime() {
        return regTime;
    }

    public void setRegTime(Timestamp regTime) {
        this.regTime = regTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    @JsonIgnore
    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authorities;
    }
}
