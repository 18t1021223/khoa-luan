package com.blogads.filter;

import com.blogads.exception.BlogAdsException;
import com.blogads.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.blogads.exception.BlogAdsException.ACCESS_DENIED;

@Order(1)
public class AdminEnableFilter implements Filter {

    @Getter
    @Setter
    @AllArgsConstructor
    private class White {
        private String method;
        private String uri;
    }

    private final List<White> whiteList = Arrays.asList(
            new White("*", "/admin/login"),
            new White("*", "/admin/register"),
            new White("*", "/admin/forgot-password"),
            new White("*", "/admin/new-password"),
            new White("*", "/admin/change-password"),
            new White("*", "/change-password/verify")
    );


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) servletRequest).getRequestURI();
        String method = ((HttpServletRequest) servletRequest).getMethod();

        for (White s : whiteList) {
            if (uri.contains(s.getUri())) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        if (!SecurityUtils.getCurrentAdminOrElseThrow().isEnable()) {
            throw new BlogAdsException(400, ACCESS_DENIED);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
