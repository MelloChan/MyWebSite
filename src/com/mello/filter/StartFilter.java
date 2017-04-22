package com.mello.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/20.
 * 编码设置
 */
@WebFilter(filterName = "StartFilter", urlPatterns = {"/*"})
public class StartFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("StartFilter Init!");
    }
    @Override
    public void destroy() {
        System.out.println("StartFilter Destroy!");
    }

    /**
     * 一个总的验证路由 统一设置全局请求编码为 UTF-8
     * @param servletRequest  请求头
     * @param servletResponse 响应头
     * @param filterChain 过滤链
     * @throws IOException IO流异常
     * @throws ServletException servlet异常
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setCharacterEncoding("UTF-8");
        //打印过滤至此的时间
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String date=simpleDateFormat.format(new Date());
        System.out.println(date+" Start Filter");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
