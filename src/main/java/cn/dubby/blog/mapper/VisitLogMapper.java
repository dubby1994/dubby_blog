package cn.dubby.blog.mapper;

import cn.dubby.blog.entity.Blog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by teeyoung on 17/12/5.
 */
@Mapper
public interface VisitLogMapper {

    @Insert("INSERT INTO visit_log(ip) values(#{ip})")
    int log(String ip);

}
