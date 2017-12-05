package cn.dubby.blog.entity;

import java.util.Date;

/**
 * Created by teeyoung on 17/12/5.
 */
public class BlogTag {

    private Long blogId;

    private Long tagId;

    private Date createTime;

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
