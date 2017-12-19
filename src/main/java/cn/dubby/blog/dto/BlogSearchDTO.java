package cn.dubby.blog.dto;

import cn.dubby.blog.entity.Blog;

/**
 * Created by teeyoung on 17/12/19.
 */
public class BlogSearchDTO extends Blog {

    private int score = 1;

    public BlogSearchDTO(Blog blog) {
        super.setId(blog.getId());
        super.setTitle(blog.getTitle());
        super.setDescription(blog.getDescription());
        super.setCreateTime(blog.getCreateTime());
        super.setUpdateTime(blog.getUpdateTime());
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        this.score++;
    }
}
