package com.yiking.blog.controller;

import com.yiking.blog.entities.Comments;
import com.yiking.blog.entities.RespBean;
import com.yiking.blog.entities.User;
import com.yiking.blog.service.CommentsService;
import com.yiking.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "评论管理")
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    UserService userService;
    @Autowired
    CommentsService commentsService;
    @PostMapping("/addcomment")
    @ApiOperation(value = "添加评论",notes = "若为评论isRead为置1，若为回复isRead置2")
    public RespBean addNewComments(Comments comments){
        User user = userService.getUserByName(comments.getUsername());
        comments.setUid(user.getId());
        int result = commentsService.addNewComments(comments);
        if (result == 1) {
            return new RespBean("success", comments.getId() + "");
        } else {
            return new RespBean("error", "评论发表失败!" );
        }
    }
    @GetMapping("/listcomments/{aid}")
    @ApiOperation(value = "查询博文的评论")
    public List<Map> getCommentsByAid(@PathVariable Long aid){
        List<Map>  res=new ArrayList<>();

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        List<Comments> comments = commentsService.getCommentsByAid(aid);

        for (Comments c:comments) {
            HashMap<String,Object> content = new HashMap<>();
            content.put("comment",c.getContent());
            content.put("id",c.getId());
            String date = dateFormat.format(c.getPublishDate());
            content.put("publishDate",date);
            User user = userService.getUserById(c.getUid());
            content.put("reply",c.getReplyName());
            content.put("author",user.getUsername());
            res.add(content);
            content=null;
            user=null;
        }
        //System.out.println(res.get(0).get("comment"));
        return res;
    }
    @PostMapping("/updatecomments")
    @ApiOperation(value = "修改评论状态",notes = "将评论状态标志位isRead置0")
    public RespBean updateCommentsByAidAndUid(Long aid,String username){
        User user = userService.getUserByName(username);

        int i = commentsService.updateComments(aid);
        if (i>0){
            return new RespBean("success","修改成功");

        }else {
            return new RespBean("error","修改失败");}
    }
    @PostMapping("/updatereply")
    @ApiOperation(value = "修改回复状态",notes = "将评论状态标志位isRead置0")
    public RespBean updateReply(Long aid,String username){
        User user = userService.getUserByName(username);

        int i = commentsService.updateReply(aid, username);
        if (i>0){
            return new RespBean("success","修改成功");
        }else {
            return new RespBean("error","修改失败");}
    }
}
