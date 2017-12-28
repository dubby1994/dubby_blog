package cn.dubby.blog;

import cn.dubby.blog.entity.Blog;
import cn.dubby.blog.mapper.BlogMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplication {

    @Autowired
    ApplicationContext ctx;

    @Autowired
    BlogMapper blogMapper;

    @Ignore
    public void testContextLoads() throws URISyntaxException, IOException {
        assertThat(this.ctx).isNotNull();
        assertThat(this.ctx.getBean(BlogMapper.class)).isNotNull();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Blog> blogList = blogMapper.listWithContent(0, 1000);
        OkHttpClient okHttpClient = new OkHttpClient();

        for (Blog blog : blogList) {
            Request request = new Request.Builder()
                    .url("http://localhost:9200/dubby_blog/blog/" + blog.getId())
                    .put(RequestBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(blog)))
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            System.out.println(request.body().toString());

        }


    }

}
