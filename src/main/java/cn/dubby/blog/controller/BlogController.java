package cn.dubby.blog.controller;

import cn.dubby.blog.entity.Blog;
import cn.dubby.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by teeyoung on 17/12/5.
 */
@RestController
@RequestMapping(value = "blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "list")
    public List<Blog> list(int offset, int limit) {
        return blogService.list(offset, limit);
    }

    @RequestMapping(value = "{id}")
    public Blog findById(@PathVariable("id") long id) {
        return blogService.findById(id);
    }

}
