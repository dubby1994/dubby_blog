package cn.dubby.blog.mapper;

import cn.dubby.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by teeyoung on 17/12/5.
 */
@Mapper
public interface TagMapper {

    @Select("SELECT * FROM tag ORDER BY createTime DESC LIMIT #{offset}, #{limit}")
    List<Tag> list(@Param(value = "offset") int offset, @Param(value = "limit") int limit);

    @Select("SELECT * FROM tag WHERE ID = #{id}")
    Tag findById(long id);

    @Select("SELECT count(id) FROM tag")
    int count();

    @Select("SELECT * FROM tag ORDER BY createTime DESC")
    List<Tag> all();
}
