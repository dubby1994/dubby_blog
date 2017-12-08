package cn.dubby.blog.controller;

import cn.dubby.blog.mapper.VisitLogMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by teeyoung on 17/12/6.
 */
@RestController
public class HitController {

    private Logger logger = LoggerFactory.getLogger(HitController.class);

    @Autowired
    private VisitLogMapper visitLogMapper;

    @RequestMapping(value = "hit")
    public Object hit(HttpServletRequest httpServletRequest) {
        String ip;
        ip = httpServletRequest.getHeader("X-Real-IP");
        if (StringUtils.isEmpty(ip))
            ip = httpServletRequest.getHeader("REMOTE-HOST");
        if (StringUtils.isEmpty(ip))
            ip = httpServletRequest.getHeader("X-Forwarded-For");

        Cookie[] cookies = httpServletRequest.getCookies();
        ObjectMapper mapper = new ObjectMapper();
        String cookie;
        try {
            cookie = mapper.writeValueAsString(cookies);
        } catch (JsonProcessingException e) {
            logger.error("cookie to json error", e);
            cookie = "cookie to json error";
        }

        if (!StringUtils.isEmpty(ip))
            visitLogMapper.log(ip, cookie, httpServletRequest.getHeader("User-Agent"));

        return "SUCCESS";
    }

}
