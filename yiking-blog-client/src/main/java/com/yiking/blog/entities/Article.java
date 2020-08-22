package com.yiking.blog.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@ApiModel(description = "博文实体")
public class Article implements Serializable {
    @ApiModelProperty(value = "博文id", position = 1)
    private Long id;
    @ApiModelProperty(value = "博文标题", position = 2)
    private String title;
    @ApiModelProperty(value = "md格式博文内容", position = 3)
    private String mdContent;
    @ApiModelProperty(value = "html格式博文内容", position = 4)
    private String htmlContent;
    @ApiModelProperty(value = "博文摘要", position = 5)
    private String summary;
    @ApiModelProperty(value = "博文类别id", position = 6)
    private Long cid;
    @ApiModelProperty(value = "博文作者id", position = 7)
    private Long uid;
    @ApiModelProperty(value = "博文发表时间", position = 8)
    private Timestamp publishDate;
    @ApiModelProperty(value = "博文状态（1为已发表，2为在回收站）", position = 9)
    private Integer state;
    @ApiModelProperty(value = "博文浏览量", position = 10)
    private Integer pageView;
    @ApiModelProperty(value = "博文编辑时间", position = 11)
    private Timestamp editTime;
    @ApiModelProperty(value = "博文标签", position = 12)
    private String[] dynamicTags;
    @ApiModelProperty(value = "博文类别", position = 13)
    private String cateName;
    @ApiModelProperty(value = "博文标签", position = 14)
    private List<Tags> tags;
    @ApiModelProperty(value = "博文重复率", position = 15)
    public Double repeatRate;
    @ApiModelProperty(value = "博文作者", position = 16)
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Double getRepeatRate() {
        return repeatRate;
    }

    public void setRepeatRate(Double repeatRate) {
        this.repeatRate = repeatRate;
    }


    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String[] getDynamicTags() {
        return dynamicTags;
    }

    public void setDynamicTags(String[] dynamicTags) {
        this.dynamicTags = dynamicTags;
    }

    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMdContent() {
        return mdContent;
    }

    public void setMdContent(String mdContent) {
        this.mdContent = mdContent;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }
}
