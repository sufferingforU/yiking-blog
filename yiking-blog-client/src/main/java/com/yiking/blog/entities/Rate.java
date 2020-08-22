package com.yiking.blog.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel(description = "博文评分实体")
public class Rate implements Serializable {
    @ApiModelProperty(value = "博文评分id", position = 1)
    private Long id;
    @ApiModelProperty(value = "评分博文id", position = 2)
    private Long aid;
    @ApiModelProperty(value = "评分作者id", position = 3)
    private Long uid;
    @ApiModelProperty(value = "评分值", position = 4)
    private Integer rate;
    @ApiModelProperty(value = "评分博文作者姓名", position = 5)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
