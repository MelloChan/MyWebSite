package com.mello.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 图片保护过滤器
 */
@WebFilter(filterName = "ImageProtectorFilter", urlPatterns = {"*.jpg", "*.png", "*.git"}, asyncSupported = true)
public class ImageProtectorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("ImageFilter Init!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String referrer = httpServletRequest.getHeader("referer");
        System.out.println("referrer:" + referrer);
        if (referrer != null)
            filterChain.doFilter(servletRequest, servletResponse);
        else {
            throw new ServletException("Image not available");
        }
    }

    @Override
    public void destroy() {
        System.out.println("ImageFilter Destroy!");
    }
}
