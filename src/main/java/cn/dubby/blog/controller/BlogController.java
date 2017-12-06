package cn.dubby.blog.controller;

import cn.dubby.blog.dto.DetailPageDTO;
import cn.dubby.blog.dto.PageDTO;
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

    @RequestMapping(value = "page")
    public PageDTO page(int offset, int limit) {
        PageDTO pageDTO = new PageDTO();
        if (offset > 0)
            pageDTO.setHasPre(true);

        int total = blogService.count();
        if (total > offset + limit)
            pageDTO.setHasNext(true);

        return pageDTO;
    }

    @RequestMapping(value = "detail/page")
    public DetailPageDTO detailPage(int id) {
        Blog pre = blogService.getPreBlog(id);
        Blog next = blogService.getNextBlog(id);

        DetailPageDTO pageDTO = new DetailPageDTO();
        if (pre != null && pre.getId() != null && pre.getId() > 0) {
            pageDTO.setHasPre(true);
            pageDTO.setPre(pre.getId());
            pageDTO.setPreTitle(pre.getTitle());
        }
        if (next != null && next.getId() != null && next.getId() > 0) {
            pageDTO.setHasNext(true);
            pageDTO.setNext(next.getId());
            pageDTO.setNextTitle(next.getTitle());
        }

        return pageDTO;
    }
}
