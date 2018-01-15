package cn.dubby.blog.service;

import cn.dubby.blog.dto.ModifyResponse;
import cn.dubby.blog.entity.Comment;
import cn.dubby.blog.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangzheng03 on 2018/1/15.
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public ModifyResponse addComment(String content, long blogId) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setBlogId(blogId);
        int result = commentMapper.insert(comment);

        if (result == 1) {
            return ModifyResponse.MODIFY_SUCCESS;
        }
        return ModifyResponse.MODIFY_SYSTEM_ERROR;
    }

    public List<Comment> list(long blogId) {
        return commentMapper.searchByBlogId(blogId);
    }

}
