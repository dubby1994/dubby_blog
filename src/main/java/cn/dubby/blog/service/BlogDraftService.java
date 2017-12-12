package cn.dubby.blog.service;

import cn.dubby.blog.config.Config;
import cn.dubby.blog.dto.ModifyResponse;
import cn.dubby.blog.entity.Blog;
import cn.dubby.blog.entity.BlogDraft;
import cn.dubby.blog.entity.Configuration;
import cn.dubby.blog.mapper.BlogDraftMapper;
import cn.dubby.blog.mapper.BlogMapper;
import cn.dubby.blog.mapper.ConfigurationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by teeyoung on 17/12/5.
 */
@Service
public class BlogDraftService {

    @Autowired
    private BlogDraftMapper blogDraftMapper;

    @Autowired
    private ConfigurationMapper configurationMapper;

    @Autowired
    private BlogMapper blogMapper;

    public List<BlogDraft> list(int offset, int limit) {
        return blogDraftMapper.list(offset, limit);
    }

    public BlogDraft findById(long id) {
        return blogDraftMapper.findById(id);
    }

    public int count() {
        return blogDraftMapper.count();
    }

    public BlogDraft getPreBlog(long id) {
        return blogDraftMapper.getPreBlog(id);
    }

    public BlogDraft getNextBlog(long id) {
        return blogDraftMapper.getNextBlog(id);
    }

    public ModifyResponse save(Long id, String title, String description, String content, String token) {
        Configuration correctToken = configurationMapper.findConfig(Config.TOKEN);
        if (correctToken == null || !correctToken.getValue().equals(token)) {
            return ModifyResponse.MODIFY_UN_LOGIN;
        }


        if (id == null || id <= 0) {
            int result = blogDraftMapper.insert(title, description, content);
            if (result == 0) {
                return ModifyResponse.MODIFY_SYSTEM_ERROR;
            }
            return ModifyResponse.MODIFY_SUCCESS;
        } else {
            int result = blogDraftMapper.update(title, description, content, id);
            if (result == 0) {
                return ModifyResponse.MODIFY_NOT_FOUND;
            }
            return ModifyResponse.MODIFY_SUCCESS;
        }
    }

    public ModifyResponse post(Long id, String token) {
        Configuration correctToken = configurationMapper.findConfig(Config.TOKEN);
        if (correctToken == null || !correctToken.getValue().equals(token)) {
            return ModifyResponse.MODIFY_UN_LOGIN;
        }
        BlogDraft blogDraft = blogDraftMapper.findById(id);
        if (blogDraft == null)
            return ModifyResponse.MODIFY_NOT_FOUND;

        Long blogId = blogDraft.getBlogId();
        if (blogDraft.getBlogId() != null && blogDraft.getBlogId() > 0) {
            //更新
            blogMapper.update(blogDraft.getTitle(), blogDraft.getDescription(), blogDraft.getContent(), blogDraft.getBlogId());
        } else {
            //插入
            Blog blog = new Blog();
            blog.setTitle(blogDraft.getTitle());
            blog.setDescription(blogDraft.getDescription());
            blog.setContent(blogDraft.getContent());
            blogMapper.insert(blog);
            blogDraftMapper.updateBlogId(id, blog.getId());
            blogId = blog.getId();
        }

        Map result = new HashMap<>();
        result.put("blogId", blogId);
        return new ModifyResponse(ModifyResponse.SUCCESS, result);
    }
}
