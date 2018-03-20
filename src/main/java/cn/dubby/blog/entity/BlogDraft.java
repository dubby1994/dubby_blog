package cn.dubby.blog.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 博客草稿，没有确认不会展示在网站上
 * Created by teeyoung on 17/12/5.
 */
public class BlogDraft implements Serializable {

    private Long id;

    private String title;

    private String description;

    private String content;

    private Date createTime;

    private Date updateTime;

    /** 对应的正式博客的 id */
    private Long blogId;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
