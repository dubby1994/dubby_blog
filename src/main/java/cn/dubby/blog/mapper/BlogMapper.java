package cn.dubby.blog.mapper;

import cn.dubby.blog.entity.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by teeyoung on 17/12/5.
 */
@Mapper
public interface BlogMapper {

    @Select("SELECT id, title, description, createTime, content FROM blog WHERE LOWER(title) like LOWER(CONCAT('%',#{title},'%'))  ORDER BY createTime DESC  LIMIT 10")
    List<Blog> searchByTitle(@Param(value = "title") String title);

    @Select("SELECT id, title, description, createTime, content FROM blog WHERE LOWER(content) like LOWER(CONCAT('%',#{content},'%'))  ORDER BY createTime DESC  LIMIT 10")
    List<Blog> searchByContent(@Param(value = "content") String content);

    @Select("SELECT id, title, description, createTime, content FROM blog WHERE LOWER(description) like LOWER(CONCAT('%',#{description},'%')) ORDER BY createTime DESC  LIMIT 10")
    List<Blog> searchByDescription(@Param(value = "description") String description);

    @Select("SELECT id, title, description, createTime FROM blog ORDER BY createTime DESC LIMIT #{offset}, #{limit}")
    List<Blog> list(@Param(value = "offset") int offset, @Param(value = "limit") int limit);

    @Select("SELECT id, title, description, createTime, content FROM blog ORDER BY createTime DESC LIMIT #{offset}, #{limit}")
    List<Blog> listWithContent(@Param(value = "offset") int offset, @Param(value = "limit") int limit);

    @Select("SELECT * FROM blog WHERE ID = #{id}")
    Blog findById(long id);

    @Select("SELECT id, title, description, createTime FROM blog WHERE ID = #{id}")
    Blog findByIdWithoutContent(long id);

    @Select("SELECT COUNT(id) FROM blog")
    int count();

    @Select("SELECT id, title FROM blog WHERE id < #{id} ORDER BY id DESC LIMIT 1")
    Blog getPreBlog(long id);

    @Select("SELECT id, title FROM blog WHERE id > #{id} ORDER BY id ASC LIMIT 1")
    Blog getNextBlog(long id);

    @Insert(value = "INSERT INTO blog(title, description, content) VALUES(#{title}, #{description}, #{content})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    int insert(Blog blog);

    @Update(value = "UPDATE blog SET title = #{title}, description = #{description}, content = #{content} WHERE id = #{id}")
    int update(@Param(value = "title") String title, @Param(value = "description") String description, @Param(value = "content") String content, @Param(value = "id") Long id);
}
