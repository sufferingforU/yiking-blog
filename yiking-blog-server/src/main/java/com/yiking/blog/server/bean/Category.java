package com.yiking.blog.server.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by  yiking 2020/07/03
 */
public class Category implements Serializable {
    private Long id;
    private String cateName;
    private Timestamp date;
    private Integer priority;

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

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
