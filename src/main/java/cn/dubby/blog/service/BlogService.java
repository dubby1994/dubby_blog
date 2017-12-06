package cn.dubby.blog.service;

import cn.dubby.blog.entity.Blog;
import cn.dubby.blog.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by teeyoung on 17/12/5.
 */
@Service
public class BlogService {

    @Autowired
    private BlogMapper blogMapper;

    public List<Blog> list(int offset, int limit) {
        return blogMapper.list(offset, limit);
    }

    public Blog findById(long id) {
        return blogMapper.findById(id);
    }

    public int count() {
        return blogMapper.count();
    }

    public Blog getPreBlog(long id) {
        return blogMapper.getPreBlog(id);
    }

    public Blog getNextBlog(long id) {
        return blogMapper.getNextBlog(id);
    }
}
