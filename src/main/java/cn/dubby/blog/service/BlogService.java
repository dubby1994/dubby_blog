package cn.dubby.blog.service;

import cn.dubby.blog.entity.Blog;
import cn.dubby.blog.entity.BlogTag;
import cn.dubby.blog.mapper.BlogMapper;
import cn.dubby.blog.mapper.BlogTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by teeyoung on 17/12/5.
 */
@Service
public class BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

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

    public List<Blog> listByTag(long[] tagIds) {
        if (tagIds == null || tagIds.length == 0) {
            return blogMapper.list(0, 10);
        }

        Set<Long> blogIdSet = new HashSet<>();
        List<BlogTag> tempBlogTag = blogTagMapper.findByTagId(tagIds[0]);
        for (BlogTag blogTag : tempBlogTag) {
            boolean isValid = true;
            for (int i = 1; i < tagIds.length; ++i) {
                int exist = blogTagMapper.exist(blogTag.getBlogId(), tagIds[i]);
                if (exist < 1) {
                    isValid = false;
                    break;
                }
            }

            if (isValid) {
                blogIdSet.add(blogTag.getBlogId());
            }
        }

        List<Blog> result = new ArrayList<>(blogIdSet.size());
        for (Long blogId : blogIdSet) {
            result.add(blogMapper.findByIdWithoutContent(blogId));
        }

        return result;
    }

    public Set<Blog> search(String keyword) {
        if (StringUtils.isEmpty(keyword)) return Collections.emptySet();
        List<Blog> titleList = blogMapper.searchByTitle(keyword);
        List<Blog> descList = blogMapper.searchByDescription(keyword);
        List<Blog> contentList = blogMapper.searchByContent(keyword);

        Set<Blog> result = new HashSet<>();
        result.addAll(titleList);
        result.addAll(descList);
        result.addAll(contentList);
        return result;
    }
}
