package com.mello.controller;

import com.mello.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/3/2.
 * 验证注册邮箱的唯一性
 */
@WebServlet(name = "CheckServlet", urlPatterns = {"/check"})
public class CheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        System.out.println(email);
        resp.setContentType("text/plain;charset=utf-8");
        try (PrintWriter pw = resp.getWriter()) {
            boolean flag = new UserServiceImpl().EmailVerify(email);
            if (flag) {
                pw.print("SUCCESS");
            } else {
                pw.print("ERROR");
            }
        } catch (IOException e) {
            System.out.println("check:" + e.toString());
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
