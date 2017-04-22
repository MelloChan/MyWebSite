package com.mello.controller;

import com.mello.listener.SessionListener;
import com.mello.util.VerifyUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/3/17.
 * 获取用户昵称
 */
@WebServlet(name = "GetNameServlet", urlPatterns = {"/ws/username"})
public class GetNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        String email = VerifyUtil.getEmail();
        System.out.println(email);
        String username = SessionListener.getUserName(email);
        try (PrintWriter pw = resp.getWriter()) {
            pw.print(username);
        } catch (IOException e) {
            System.out.println("username:" + e.toString());
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
