package com.yiking.blog.server.bean;

import java.io.Serializable;
/**
 * Created by  yiking 2020/07/03
 */
public class Tags  implements Serializable {
    private Long id;
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
