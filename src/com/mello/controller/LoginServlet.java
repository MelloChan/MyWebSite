package com.mello.controller;

import com.mello.entity.User;
import com.mello.listener.SessionListener;
import com.mello.service.impl.UserServiceImpl;
import com.mello.util.DigestUtil;
import com.mello.util.VerifyUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/2/26.
 * 用于验证用户登录
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean flag = verifyUser(req, resp);
        if (flag) {
            resp.sendRedirect("/main/html/main.html");
            return;
        }
        resp.sendRedirect("/html/error.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 验证用户账户信息 获取成功设置相应的唯一session
     *
     * @param request
     * @param response
     * @return 当存在该用户时返回真 当用户不存在时返回假，用户重复登录时跳转回聊天主页面
     * @throws UnsupportedEncodingException
     */
    private Boolean verifyUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = getUserInfo(request);
        if (user == null) {
            return false;
        }
        String email = user.getEmail();
        String password = user.getPassword();
        boolean flag = new UserServiceImpl().LoginVerify(email, password);
        if (flag) {
            user.setUsername(VerifyUtil.getUsername());
            HttpSession session = request.getSession();
            session.setAttribute(email, user);
            SessionListener.addUser(email, (User) session.getAttribute(email));
        }
        return flag;
    }

    /**
     * 组装返回用户实例
     *
     * @param request
     * @return 当用户未在session时返回User对象，否则返回null
     * @throws UnsupportedEncodingException
     */
    private User getUserInfo(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String email = request.getParameter("email");
        String password = DigestUtil.digestMD5(request.getParameter("password"));
        if (SessionListener.hasSession(email)) {
            return null;
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

}
