package cn.dubby.blog.task;

import cn.dubby.blog.entity.Blog;
import cn.dubby.blog.entity.Tag;
import cn.dubby.blog.mapper.BlogMapper;
import cn.dubby.blog.mapper.BlogTagMapper;
import cn.dubby.blog.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by teeyoung on 17/12/7.
 */
@Component
public class ScheduledTagTasks {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;


    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void generateTag() {
        System.out.println("generateTag started");
        int tagCount = tagMapper.count();
        List<Tag> tagList = tagMapper.list(0, tagCount);

        int blogCount = blogMapper.count();

        for (int offset = 0, limit = 10; offset < blogCount; offset += limit) {
            //按批次取出博客
            List<Blog> blogList = blogMapper.listWithContent(offset, limit);
            for (Blog blog : blogList) {
                if (StringUtils.isEmpty(blog.getContent())) continue;

                //每个博客处理标签
                Set<Long> containTagIdSet = new HashSet<>();

                String content = blog.getContent().toLowerCase();
                String title = blog.getTitle().toLowerCase();
                String description = null;
                if (blog.getDescription() != null) {
                    description = blog.getDescription().toLowerCase();
                }


                for (Tag tag : tagList) {
                    if (content.contains(tag.getName().toLowerCase())) {
                        containTagIdSet.add(tag.getId());
                        continue;
                    }

                    if (title.contains(tag.getName().toLowerCase())) {
                        containTagIdSet.add(tag.getId());
                        continue;
                    }

                    if (description != null && description.contains(tag.getName().toLowerCase())) {
                        containTagIdSet.add(tag.getId());
                    }
                }

                //删除旧的标签关系
                blogTagMapper.deleteByBlogId(blog.getId());

                //增加新的标签关系
                for (Long tagId : containTagIdSet) {
                    blogTagMapper.insert(blog.getId(), tagId);
                }
            }
        }
        System.out.println("generateTag completed");
    }

//    @Scheduled(cron = "10 * * * * *")
//    public void cron() {
//    }

}
