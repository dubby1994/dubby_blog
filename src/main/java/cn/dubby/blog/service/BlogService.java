package cn.dubby.blog.service;

import cn.dubby.blog.dto.BlogSearchDTO;
import cn.dubby.blog.entity.Blog;
import cn.dubby.blog.entity.BlogTag;
import cn.dubby.blog.mapper.BlogMapper;
import cn.dubby.blog.mapper.BlogTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by teeyoung on 17/12/5.
 */
@Service
public class BlogService {

    private static final Logger logger = LoggerFactory.getLogger(BlogService.class);

    private static final ThreadGroup SEARCH_THREAD_GROUP = new ThreadGroup("search_thread_group");
    private static final ExecutorService SEARCH_EXECUTOR = Executors.newFixedThreadPool(10, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(SEARCH_THREAD_GROUP, r, "search_thread");
        }
    });

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

    public List<Blog> list(int offset, int limit) {
        return blogMapper.list(offset, limit);
    }

    public Blog findById(long id) {
        return blogMapper.findById(id);
    }

    public int count() {
        return blogMapper.count();
    }

    public Blog getPreBlog(long id) {
        return blogMapper.getPreBlog(id);
    }

    public Blog getNextBlog(long id) {
        return blogMapper.getNextBlog(id);
    }

    public List<Blog> listByTag(long[] tagIds) {
        if (tagIds == null || tagIds.length == 0) {
            return blogMapper.list(0, 10);
        }

        Set<Long> blogIdSet = new HashSet<>();
        List<BlogTag> tempBlogTag = blogTagMapper.findByTagId(tagIds[0]);
        for (BlogTag blogTag : tempBlogTag) {
            boolean isValid = true;
            for (int i = 1; i < tagIds.length; ++i) {
                int exist = blogTagMapper.exist(blogTag.getBlogId(), tagIds[i]);
                if (exist < 1) {
                    isValid = false;
                    break;
                }
            }

            if (isValid) {
                blogIdSet.add(blogTag.getBlogId());
            }
        }

        List<Blog> result = new ArrayList<>(blogIdSet.size());
        for (Long blogId : blogIdSet) {
            result.add(blogMapper.findByIdWithoutContent(blogId));
        }

        return result;
    }

    public List<BlogSearchDTO> search(String keyword) {
        if (StringUtils.isEmpty(keyword)) return Collections.emptyList();

        String[] keywords = keyword.split(",|\\s");
        List<Future<List<BlogSearchDTO>>> futureList = new ArrayList<>(keywords.length);
        for (String key : keywords) {
            if (!StringUtils.isEmpty(key))
                futureList.add(searchSingleKeyword(key));
        }

        if (futureList.size() == 0)
            return Collections.emptyList();
        List<BlogSearchDTO> result = new ArrayList<>();
        try {
            List<BlogSearchDTO> blogList0 = futureList.get(0).get();
            for (BlogSearchDTO dto : blogList0) {
                boolean isValid = true;
                for (int i = 1; i < futureList.size(); ++i) {
                    List<BlogSearchDTO> blogList_i = futureList.get(i).get();
                    boolean contain = false;
                    for (BlogSearchDTO dto_i : blogList_i) {
                        if (dto_i.getId().equals(dto.getId())) {
                            contain = true;
                        }
                    }
                    if (!contain) {
                        isValid = false;
                        break;
                    }
                }
                if (isValid) {
                    result.add(dto);
                }
            }
        } catch (Exception e) {
            logger.error("get blog list future", e);
        }

        return result;
    }

    private Future<List<BlogSearchDTO>> searchSingleKeyword(String keyword) {
        return SEARCH_EXECUTOR.submit(new Callable<List<BlogSearchDTO>>() {
            @Override
            public List<BlogSearchDTO> call() throws Exception {
                if (StringUtils.isEmpty(keyword)) return Collections.emptyList();
                List<Blog> titleList = blogMapper.searchByTitle(keyword);
                List<Blog> descList = blogMapper.searchByDescription(keyword);
                List<Blog> contentList = blogMapper.searchByContent(keyword);

                List<Blog> blogList = titleList;
                blogList.addAll(descList);
                blogList.addAll(contentList);

                Map<Long, BlogSearchDTO> map = new HashMap<>();
                for (Blog blog : blogList) {
                    if (map.containsKey(blog.getId())) {
                        map.get(blog.getId()).increaseScore();
                    } else {
                        map.put(blog.getId(), new BlogSearchDTO(blog));
                    }
                }

                List<BlogSearchDTO> result = new ArrayList<>(map.size());
                result.addAll(map.values());

                Collections.sort(result, new Comparator<BlogSearchDTO>() {

                    @Override
                    public int compare(BlogSearchDTO o1, BlogSearchDTO o2) {
                        return o2.getScore() - o1.getScore();
                    }
                });


                logger.warn("searchSingleKeyword:" + keyword);
                return result;
            }
        });
    }
}
