package cn.dubby.blog.controller;

import cn.dubby.blog.dto.TagDTO;
import cn.dubby.blog.entity.Tag;
import cn.dubby.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by teeyoung on 17/12/7.
 */
@RestController
@RequestMapping(value = "tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "top")
    public List<TagDTO> listTopTag(int limit) {
        if (limit > 100)
            limit = 100;
        return tagService.listTopTag(limit);
    }

    @RequestMapping(value = "all")
    public List<Tag> all() {
        return tagService.all();
    }
}
