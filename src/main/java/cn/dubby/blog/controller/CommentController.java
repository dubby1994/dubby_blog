package cn.dubby.blog.controller;

import cn.dubby.blog.dto.ModifyResponse;
import cn.dubby.blog.entity.Comment;
import cn.dubby.blog.service.CommentService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangzheng03 on 2018/1/15.
 */
@RestController
@RequestMapping(value = "comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ModifyResponse addComment(String content, long blogId) {
        if (StringUtils.isEmpty(content)) {
            return ModifyResponse.MODIFY_SUCCESS;
        }
        return commentService.addComment(content, blogId);
    }

    @RequestMapping(value = "list")
    public List<Comment> list(long blogId) {
        List<Comment> result = commentService.list(blogId);

        for (Comment comment : result) {
            comment.setContent(StringEscapeUtils.escapeHtml4(comment.getContent()));
        }

        return result;
    }

}
