package com.mello.controller;

import com.mello.entity.User;
import com.mello.service.impl.UserServiceImpl;
import com.mello.util.DigestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by Administrator on 2017/2/26.
 * 处理用户注册程序
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getUserInfo(req);
        boolean flag = new UserServiceImpl().register(user);
        if (flag) {
            resp.sendRedirect("/html/login.html");
            return;
        }
        resp.sendRedirect("/html/error.html");

    }

    private User getUserInfo(HttpServletRequest request) {
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(DigestUtil.digestMD5(request.getParameter("password")));
        user.setEmail(request.getParameter("email"));
        user.setRegistrationTime(new Date(System.currentTimeMillis()));
        user.setIp(request.getRemoteAddr());
        return user;
    }
}
