package cn.dubby.blog.service;

import cn.dubby.blog.dto.TagDTO;
import cn.dubby.blog.entity.Tag;
import cn.dubby.blog.mapper.BlogTagMapper;
import cn.dubby.blog.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teeyoung on 17/12/5.
 */
@Service
public class TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

    public List<Tag> list(int offset, int limit) {
        return tagMapper.list(offset, limit);
    }

    public Tag findById(long id) {
        return tagMapper.findById(id);
    }

    /**
     * 查出关联最多的前 n 个标签
     *
     * @param limit
     * @return
     */
    public List<TagDTO> listTopTag(int limit) {
        List<TagDTO> tagList = blogTagMapper.findTop(limit);

        for (TagDTO tagDTO : tagList) {
            tagDTO.setTagName(tagMapper.findById(tagDTO.getTagId()).getName());
        }

        return tagList;
    }

    public List<Tag> all() {
        return tagMapper.all();
    }
}
