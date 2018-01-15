package cn.dubby.blog.mapper;

import cn.dubby.blog.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yangzheng03 on 2018/1/15.
 */
@Mapper
public interface CommentMapper {

    @Insert(value = "INSERT INTO comment(blogId, content) VALUES(#{blogId}, #{content})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    int insert(Comment comment);

    @Select("SELECT id, blogId, content, createTime FROM comment WHERE blogId = #{blogId}")
    List<Comment> searchByBlogId(@Param(value = "blogId") long blogId);

}
