package com.yiking.blog.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel(description = "博文搜索条件实体")
public class SearchCondition implements Serializable {
    @ApiModelProperty(value = "搜索条件id", position =1)
    private Long id;
    @ApiModelProperty(value = "搜索条件频率", position = 2)
    private int frequency;
    @ApiModelProperty(value = "搜索条件内容", position = 3)
    private String searchCondition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }
}
