package cn.dubby.blog.controller;

import cn.dubby.blog.config.Config;
import cn.dubby.blog.dto.DetailPageDTO;
import cn.dubby.blog.dto.ModifyResponse;
import cn.dubby.blog.dto.PageDTO;
import cn.dubby.blog.entity.BlogDraft;
import cn.dubby.blog.service.BlogDraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by teeyoung on 17/12/5.
 */
@RestController
@RequestMapping(value = "draft")
public class BlogDraftController {


    @Autowired
    private BlogDraftService blogDraftService;

    @RequestMapping(value = "list")
    public List<BlogDraft> list(int offset, int limit) {
        return blogDraftService.list(offset, limit);
    }

    @RequestMapping(value = "{id}")
    public BlogDraft findById(@PathVariable("id") long id) {
        return blogDraftService.findById(id);
    }

    @RequestMapping(value = "page")
    public PageDTO page(int offset, int limit) {
        PageDTO pageDTO = new PageDTO();
        if (offset > 0)
            pageDTO.setHasPre(true);

        int total = blogDraftService.count();
        if (total > offset + limit)
            pageDTO.setHasNext(true);

        return pageDTO;
    }

    @RequestMapping(value = "detail/page")
    public DetailPageDTO detailPage(int id) {
        BlogDraft pre = blogDraftService.getPreBlog(id);
        BlogDraft next = blogDraftService.getNextBlog(id);

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

    @RequestMapping(value = "save")
    public ModifyResponse save(Long id, String title, String description, String content, HttpServletRequest httpServletRequest) {
        String tokenInCookie = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie : cookies) {
            if (Config.TOKEN.equals(cookie.getName())) {
                tokenInCookie = cookie.getValue();
                break;
            }
        }
        if (StringUtils.isEmpty(tokenInCookie)) {
            return ModifyResponse.MODIFY_UN_LOGIN;
        }

        return blogDraftService.save(id, title, description, content, tokenInCookie);
    }

    @RequestMapping(value = "post")
    public ModifyResponse post(Long id, HttpServletRequest httpServletRequest) {
        String tokenInCookie = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie : cookies) {
            if (Config.TOKEN.equals(cookie.getName())) {
                tokenInCookie = cookie.getValue();
                break;
            }
        }
        if (StringUtils.isEmpty(tokenInCookie)) {
            return ModifyResponse.MODIFY_UN_LOGIN;
        }
        if (id == null) {
            return ModifyResponse.MODIFY_NOT_FOUND;
        }
        return blogDraftService.post(id, tokenInCookie);
    }
}
