package cn.dubby.blog.controller;

import cn.dubby.blog.mapper.VisitLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by teeyoung on 17/12/6.
 */
@RestController
public class HitController {

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

        if (!StringUtils.isEmpty(ip))
            visitLogMapper.log(ip);

        return "SUCCESS";
    }

}
