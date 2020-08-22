package com.yiking.blog.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by  yiking 2020/07/03
 */
@ApiModel(description = "博文类别实体")
public class Category implements Serializable {
    @ApiModelProperty(value = "博文id", position = 1)
    private Long id;
    @ApiModelProperty(value = "博文类别名称", position = 2)
    private String cateName;
    @ApiModelProperty(value = "博文最近一次编辑时间", position = 3)
    private Timestamp date;

    public Category() {
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
