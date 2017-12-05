package cn.dubby.blog.service;

import cn.dubby.blog.entity.Tag;
import cn.dubby.blog.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by teeyoung on 17/12/5.
 */
@Service
public class TagService {

    @Autowired
    private TagMapper tagMapper;

    public List<Tag> list(int offset, int limit) {
        return tagMapper.list(offset, limit);
    }

    public Tag findById(long id) {
        return tagMapper.findById(id);
    }

}
