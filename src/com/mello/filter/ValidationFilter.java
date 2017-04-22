package com.mello.filter;

import com.mello.entity.User;
import com.mello.util.VerifyUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        System.out.println("Validation Filter ");
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user= (User) request.getSession().getAttribute("user");
        String activation=VerifyUtil.getActivation();
        if(user==null){
            System.out.println("user == null");
            response.sendRedirect("/html/login.html");
        }
        if("0".equals(activation)){
            System.out.println("activation == 0");
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
