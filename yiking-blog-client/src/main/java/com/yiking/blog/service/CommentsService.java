package com.yiking.blog.service;

import com.yiking.blog.entities.Article;
import com.yiking.blog.entities.Comments;
import com.yiking.blog.entities.User;
import com.yiking.blog.mapper.ArticleMapper;
import com.yiking.blog.mapper.CommentsMapper;
import com.yiking.blog.mapper.RealContentMapper;
import com.yiking.blog.utils.SensitivewordFilter;
import com.yiking.blog.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CommentsService {
    @Autowired
    CommentsMapper commentsMapper;
    @Autowired
    RealContentMapper realContentMapper;
    @Autowired
    ArticleMapper articleMapper;
    public int addNewComments(Comments comments) {
        Timestamp timestamp = null;
        if (comments.getId() == -1) {
            //添加操作
            timestamp = new Timestamp(System.currentTimeMillis());
        }
        realContentMapper.addRealContent(comments.getContent(),timestamp,UserUtil.getCurrentUser().getId());
        SensitivewordFilter filter = SensitivewordFilter.getSensitivewordFilter();
        comments.setContent(filter.checkAndReplaceComments(comments.getContent()));
        comments.setPublishDate(timestamp);
        Article article = articleMapper.getArticleById(comments.getAid());
        comments.setParentId(article.getUid());
        comments.setIsRead(2);
        if (comments.getReplyName() == null || comments.getReplyName().equals("")) {
            comments.setReplyName("YIKINGYA");
            comments.setIsRead(1);
        }

        return commentsMapper.addComments(comments);
    }

    public List<Comments> getCommentsByParentId(Long parentId,Long isread) {
        return commentsMapper.getCommentsByParentId(parentId,isread);
    }
    public List<Comments> getCommentsByReplyName(String replyname,Long isread) {
        return commentsMapper.getCommentsByReplyName(replyname,isread);
    }
    public List<Comments> getCommentsByAid(Long aid) {

        return commentsMapper.getCommentsByAid(aid);
    }
    public int updateComments(Long aid){
        return commentsMapper.updateComments(aid);
    }
    public int updateReply(Long aid,String username){
        return commentsMapper.updateReply(aid,username);
    }
}
