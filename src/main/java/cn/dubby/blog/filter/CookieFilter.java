package cn.dubby.blog.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by teeyoung on 17/12/8.
 */
@WebFilter(
        urlPatterns = "/*"
)
public class CookieFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CookieFilter.class);

    private static final Set<String> HOST_SET = new HashSet<>();

    private static final String VISIT_ID_COOKIE = "visit_id";

    private static final String DOMAIN = "dubby.cn";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        HOST_SET.add(DOMAIN);
        HOST_SET.add("itbus.tech");
        HOST_SET.add("localhost");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (!checkRefer(httpServletRequest)) {
            logger.info(httpServletRequest.getHeader("Referer") + ":" + httpServletRequest.getRequestURI());
            return;
        }

        if (!checkUA(httpServletRequest)) {
            logger.info(httpServletRequest.getHeader("User-Agent") + ":" + httpServletRequest.getRequestURI());
            return;
        }

        Cookie[] cookies = httpServletRequest.getCookies();

        boolean isNew = true;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (VISIT_ID_COOKIE.equals(cookie.getName())) {
                    isNew = false;
                    break;
                }
            }
        }

        if (isNew) {
            Cookie cookie = new Cookie(VISIT_ID_COOKIE, UUID.randomUUID().toString());
            cookie.setMaxAge(60 * 60 * 24 * 365);
            cookie.setHttpOnly(true);
            httpServletResponse.addCookie(cookie);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean checkRefer(HttpServletRequest httpServletRequest) {
        String refer = httpServletRequest.getHeader("Referer");
        try {
            URL url = new URL(refer);
            for (String host : HOST_SET) {
                if (url.getHost().endsWith(host)) {
                    return true;
                }
            }
        } catch (MalformedURLException e) {
            logger.error("csrf refer error", refer + ":" + httpServletRequest.getRequestURI());
        }
        return false;
    }

    private boolean checkUA(HttpServletRequest httpServletRequest) {
        String ua = httpServletRequest.getHeader("User-Agent");
        if (StringUtils.isEmpty(ua) || ua.length() < 10) {
            return false;
        }
        return true;
    }

    @Override
    public void destroy() {

    }
}
