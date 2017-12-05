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

    @Select("SELECT * FROM BLOG ORDER BY createTime DESC LIMIT #{offset}, #{limit}")
    List<Blog> list(@Param(value = "offset") int offset, @Param(value = "limit") int limit);

    @Select("SELECT * FROM BLOG WHERE ID = #{id}")
    Blog findById(long id);

    @Select("SELECT COUNT(*) FROM BLOG")
    int count();
}
