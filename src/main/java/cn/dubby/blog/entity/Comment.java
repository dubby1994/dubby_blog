package cn.dubby.blog.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangzheng03 on 2018/1/15.
 */
public class Comment implements Serializable {

    private Long id;

    private Long blogId;

    private String content;

    private Date createTime;

    private Date updateTime;

    /**
     * 1:正常
     * 2:删除
     */
    private int status = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
