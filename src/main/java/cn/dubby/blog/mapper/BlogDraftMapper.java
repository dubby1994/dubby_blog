package cn.dubby.blog.mapper;

import cn.dubby.blog.entity.BlogDraft;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by teeyoung on 17/12/5.
 */
@Mapper
public interface BlogDraftMapper {

    @Insert(value = "INSERT INTO blog_draft(title, description, content) VALUES(#{title}, #{description}, #{content})")
    int insert(@Param(value = "title") String title, @Param(value = "description") String description, @Param(value = "content") String content);

    @Update(value = "UPDATE blog_draft SET title = #{title}, description = #{description}, content = #{content} WHERE id = #{id}")
    int update(@Param(value = "title") String title, @Param(value = "description") String description, @Param(value = "content") String content, @Param(value = "id") Long id);

    @Update(value = "UPDATE blog_draft SET blogId = #{blogId} WHERE id = #{id}")
    int updateBlogId(@Param(value = "id") Long id, @Param(value = "blogId") Long blogId);

    @Select("SELECT id, title, description, createTime, blogId FROM blog_draft ORDER BY createTime DESC LIMIT #{offset}, #{limit}")
    List<BlogDraft> list(@Param(value = "offset") int offset, @Param(value = "limit") int limit);

    @Select("SELECT id, title, description, createTime, content, blogId FROM blog_draft ORDER BY createTime DESC LIMIT #{offset}, #{limit}")
    List<BlogDraft> listWithContent(@Param(value = "offset") int offset, @Param(value = "limit") int limit);

    @Select("SELECT * FROM blog_draft WHERE ID = #{id}")
    BlogDraft findById(long id);

    @Select("SELECT id, title, description, createTime, blogId FROM blog_draft WHERE ID = #{id}")
    BlogDraft findByIdWithoutContent(long id);

    @Select("SELECT COUNT(id) FROM blog_draft")
    int count();

    @Select("SELECT id, title, blogId FROM blog_draft WHERE id < #{id} ORDER BY id DESC LIMIT 1")
    BlogDraft getPreBlog(long id);

    @Select("SELECT id, title, blogId FROM blog_draft WHERE id > #{id} ORDER BY id ASC LIMIT 1")
    BlogDraft getNextBlog(long id);
}
