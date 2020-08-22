package com.yiking.blog.server.utils;

import com.yiking.blog.server.bean.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by  yiking 2020/07/03
 */
public class Util {
    public static User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
