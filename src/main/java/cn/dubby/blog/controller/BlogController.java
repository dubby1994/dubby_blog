package cn.dubby.blog.controller;

import cn.dubby.blog.dto.BlogSearchDTO;
import cn.dubby.blog.dto.DetailPageDTO;
import cn.dubby.blog.dto.PageDTO;
import cn.dubby.blog.entity.Blog;
import cn.dubby.blog.service.BlogService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by teeyoung on 17/12/5.
 */
@RestController
@RequestMapping(value = "blog")
public class BlogController {

    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private BlogService blogService;

    private static final String query = "{\n" +
            "    \"query\": {\n" +
            "        \"multi_match\": {\n" +
            "            \"query\": \"%1$s\",\n" +
            "            \"fields\": [\n" +
            "                \"title\",\n" +
            "                \"description\",\n" +
            "                \"content\"\n" +
            "            ]\n" +
            "        }\n" +
            "    },\n" +
            "    \"size\": 20,\n" +
            "    \"_source\": [\n" +
            "        \"id\",\n" +
            "        \"title\",\n" +
            "        \"description\",\n" +
            "        \"createTime\"\n" +
            "    ],\n" +
            "    \"highlight\": {\n" +
            "        \"pre_tags\": [\n" +
            "            \"<font color=\\\"red\\\">\"\n" +
            "        ],\n" +
            "        \"post_tags\": [\n" +
            "            \"</font>\"\n" +
            "        ],\n" +
            "        \"fields\": {\n" +
            "            \"title\": {\n" +
            "                \n" +
            "            },\n" +
            "            \"description\": {\n" +
            "                \n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}";

    private static final OkHttpClient okHttpClient = new OkHttpClient();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "search")
    public List<BlogSearchDTO> search(String keyword) {
        try {
            String trueQuery = String.format(query, keyword);
            System.out.println(trueQuery);

            Request request = new Request.Builder()
                    .url("http://10.154.201.103:9999/dubby_blog/blog/_search")
                    .post(RequestBody.create(MediaType.parse("application/json"), trueQuery))
                    .build();
            Response response = okHttpClient.newCall(request).execute();

            List<BlogSearchDTO> result = new ArrayList<>(20);
            String jsonResult = response.body().string();
            JsonNode rootNode = objectMapper.readTree(jsonResult);
            if (rootNode.has("hits")) {
                JsonNode hitsNode = rootNode.get("hits");
                ArrayNode arrayNode = (ArrayNode) hitsNode.get("hits");
                for (int i = 0; i < arrayNode.size(); ++i) {
                    JsonNode blog = arrayNode.get(i);

                    BlogSearchDTO dto = new BlogSearchDTO();
                    dto.setId(blog.get("_source").get("id").asLong());
                    dto.setTitle(blog.get("_source").get("title").asText());

                    if (blog.get("_source").get("description") != null) {
                        dto.setDescription(blog.get("_source").get("description").asText());
                    } else {
                        dto.setDescription("暂无简介");
                    }

                    dto.setCreateTime(new Date(blog.get("_source").get("createTime").asLong()));

                    if (blog.has("highlight")) {
                        if (blog.get("highlight").has("title")) {
                            ArrayNode temp = (ArrayNode) blog.get("highlight").get("title");
                            dto.setTitle(temp.get(0).asText());
                        }

                        if (blog.get("highlight").has("description")) {
                            ArrayNode temp = (ArrayNode) blog.get("highlight").get("description");
                            dto.setDescription(temp.get(0).asText());
                        }
                    }

                    if (dto.getDescription() == null || dto.getDescription().equals("null")) {
                        dto.setDescription("暂无简介");
                    }

                    result.add(dto);
                }
            }

            return result;
        } catch (Exception e) {
            logger.error("query from es", e);
        }
        return blogService.search(keyword);
    }

    @RequestMapping(value = "list")
    public List<Blog> list(int offset, int limit) {
        return blogService.list(offset, limit);
    }

    @RequestMapping(value = "{id}")
    public Blog findById(@PathVariable("id") long id) {
        return blogService.findById(id);
    }

    @RequestMapping(value = "page")
    public PageDTO page(int offset, int limit) {
        PageDTO pageDTO = new PageDTO();
        if (offset > 0)
            pageDTO.setHasPre(true);

        int total = blogService.count();
        if (total > offset + limit)
            pageDTO.setHasNext(true);

        return pageDTO;
    }

    @RequestMapping(value = "detail/page")
    public DetailPageDTO detailPage(int id) {
        Blog pre = blogService.getPreBlog(id);
        Blog next = blogService.getNextBlog(id);

        DetailPageDTO pageDTO = new DetailPageDTO();
        if (pre != null && pre.getId() != null && pre.getId() > 0) {
            pageDTO.setHasPre(true);
            pageDTO.setPre(pre.getId());
            pageDTO.setPreTitle(pre.getTitle());
        }
        if (next != null && next.getId() != null && next.getId() > 0) {
            pageDTO.setHasNext(true);
            pageDTO.setNext(next.getId());
            pageDTO.setNextTitle(next.getTitle());
        }

        return pageDTO;
    }

    @RequestMapping(value = "list/tag")
    public List<Blog> listByTag(long[] tagId) {
        return blogService.listByTag(tagId);
    }
}
