package com.yiking.blog.utils;

import com.yiking.blog.entities.Article;
import com.yiking.blog.entities.User;
import com.yiking.blog.service.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultHelper {
    public List<Object> getResultListArticle(List<Article> articles, UserService userService, SimpleDateFormat dateFormat){
        List<Object> res = new ArrayList<>();
        for (int i=0;i<articles.size();i++) {
            Article a = articles.get(i);
            HashMap<String, Object> content = new HashMap<>();
            content.put("title", a.getTitle());
            content.put("id", a.getId());
            User user = userService.getUserById(a.getUid());
            // System.out.println(user.getUsername());
            content.put("author", user.getUsername());
            content.put("pageview",a.getPageView());
            String format = dateFormat.format(a.getPublishDate());
            content.put("publishDate", format);
            content.put("summary", a.getSummary());
            res.add(content);
            content = null;
            user = null;
        }
        return res;
    }
}
