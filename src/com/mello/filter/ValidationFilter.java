package com.mello.filter;

import com.mello.listener.SessionListener;
import com.mello.util.VerifyUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/2/26.
 * 资源验证过滤
 */
@WebFilter(filterName = "ValidationFilter", urlPatterns = {"/main/*"})
public class ValidationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("ValidationFilter Init!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String email = VerifyUtil.getEmail();
        System.out.println(email);
        boolean flag = SessionListener.hasSession(email);
        String activation=VerifyUtil.getActivation();
        System.out.println("ValidationFilter:" + flag);
        if (!flag) {
            response.sendRedirect("/html/login.html");
            return;
        }
        if("0".equals(activation)){
            servletRequest.getRequestDispatcher("/html/active.html").forward(servletRequest,servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("ValidationFilter Destroy!");
    }

}
