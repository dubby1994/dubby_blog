package cn.dubby.blog.mapper;

import cn.dubby.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by teeyoung on 17/12/5.
 */
@Mapper
public interface BlogMapper {

    @Select("SELECT id, title, description, createTime FROM BLOG ORDER BY createTime DESC LIMIT #{offset}, #{limit}")
    List<Blog> list(@Param(value = "offset") int offset, @Param(value = "limit") int limit);

    @Select("SELECT * FROM BLOG WHERE ID = #{id}")
    Blog findById(long id);

    @Select("SELECT COUNT(id) FROM BLOG")
    int count();

    @Select("SELECT id, title FROM BLOG WHERE id < #{id} ORDER BY id DESC LIMIT 1")
    Blog getPreBlog(long id);

    @Select("SELECT id, title FROM BLOG WHERE id > #{id} ORDER BY id ASC LIMIT 1")
    Blog getNextBlog(long id);
}
