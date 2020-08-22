package com.yiking.blog.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


@ApiModel(description = "博文标签实体")
public class Tags implements Serializable {
    @ApiModelProperty(value = "博文标签id", position =1)
    private Long id;
    @ApiModelProperty(value = "博文标签", position =2)
    private String tagName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
