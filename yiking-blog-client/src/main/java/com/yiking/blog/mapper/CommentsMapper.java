package com.yiking.blog.mapper;

import com.yiking.blog.entities.Comments;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentsMapper {
   int addComments(Comments comments);
   List<Comments> getCommentsByAid(@Param("aid")Long aid);
   List<Comments> getCommentsByParentId(@Param("parentid")Long parentid,@Param("isread")Long isread);
   List<Comments> getCommentsByReplyName(@Param("replyname")String replyname,@Param("isread")Long isread);
   int updateComments(@Param("aid")Long aid);
   int updateReply(@Param("aid")Long aid,@Param("username")String username);
}
