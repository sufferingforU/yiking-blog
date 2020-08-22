package com.yiking.blog.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;
@ApiModel(description = "博文评论实体")
public class Comments implements Serializable {
    @ApiModelProperty(value = "评论id", position = 1)
    private Long id;
    @ApiModelProperty(value = "评论博文id", position = 2)
    private Long aid;
    @ApiModelProperty(value = "评论用户id", position = 3)
    private Long uid;
    @ApiModelProperty(value = "评论用户名", position = 4)
    private String username;
    @ApiModelProperty(value = "评论内容", position = 5)
    private String content;
    @ApiModelProperty(value = "评论发表时间", position = 6)
    private Timestamp publishDate;
    @ApiModelProperty(value = "回复用户名", position = 7)
    private String replyName;
    @ApiModelProperty(value = "评论/回复是否已读", position = 8)
    private Integer isRead;
    @ApiModelProperty(value = "评论博文作者id", position = 9)
    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }
}
