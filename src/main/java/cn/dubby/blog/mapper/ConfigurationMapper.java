package cn.dubby.blog.mapper;

import cn.dubby.blog.entity.Configuration;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by teeyoung on 17/12/12.
 */
@Mapper
@CacheNamespace
public interface ConfigurationMapper {

    @Select(value = "SELECT * FROM configuration WHERE name = #{name}")
    Configuration findConfig(String name);

}
