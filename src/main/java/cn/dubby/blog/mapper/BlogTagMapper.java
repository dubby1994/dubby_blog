package cn.dubby.blog.mapper;

import cn.dubby.blog.dto.TagDTO;
import cn.dubby.blog.entity.BlogTag;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by teeyoung on 17/12/5.
 */
@Mapper
public interface BlogTagMapper {

    @Insert("INSERT INTO blog_tag(blogId, tagId) values(#{blogId}, #{tagId})")
    int insert(@Param(value = "blogId") long blogId, @Param(value = "tagId") long tagId);

    @Select("SELECT * FROM blog_tag WHERE blogId = #{blogId}")
    List<BlogTag> findByBlogId(long blogId);

    @Select("SELECT * FROM blog_tag WHERE tagId = #{tagId}")
    List<BlogTag> findByTagId(long tagId);

    @Select("SELECT count(*) FROM blog_tag WHERE blogId = #{blogId} and tagId = #{tagId}")
    int exist(@Param(value = "blogId") long blogId, @Param(value = "tagId") long tagId);

    @Select("SELECT COUNT(*) FROM blog_tag")
    int count();

    @Delete("DELETE FROM blog_tag WHERE blogId = #{blogId} and tagId = #{tagId}")
    int delete(@Param(value = "blogId") long blogId, @Param(value = "tagId") long tagId);

    @Delete("DELETE FROM blog_tag WHERE blogId = #{blogId}")
    int deleteByBlogId(long blogId);

    @Delete("DELETE FROM blog_tag WHERE tagId = #{tagId}")
    int deleteByTagId(long tagId);

    @Select("select tagId, count(1) as count from blog_tag group by tagId order by count desc limit #{limit}")
    List<TagDTO> findTop(int limit);
}
