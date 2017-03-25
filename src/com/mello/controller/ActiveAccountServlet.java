package com.mello.controller;

import com.mello.service.impl.UserServiceImpl;
import com.mello.util.VerifyUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/3/24.
 * 用于激活账户的servlet
 */
@WebServlet(name = "ActiveAccountServlet", urlPatterns = {"/activeAccount"})
public class ActiveAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        try (PrintWriter pw = resp.getWriter()) {
            if ("1".equals(VerifyUtil.getActivation())) {
                pw.print("<p id=\"activation\" style=\"color: green\">账号已激活</p>");
                pw.flush();
            } else {
                if (verifyInfo(req)) {
                    Integer id = VerifyUtil.getId();
                    String code = VerifyUtil.getActivationCode();
                    boolean flag = new UserServiceImpl().statusUpdate(id, "1", code);
                    if (flag) {
                        VerifyUtil.setActivation("1");
                        pw.print("<p id=\"activation\" style=\"color: red\">账号激活成功</p>");
                        pw.flush();
                        return;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/html/error.html").forward(req, resp);
    }

    private boolean verifyInfo(HttpServletRequest req) throws ServletException, IOException {
        String email = req.getParameter("email") + "";
        String key = req.getParameter("key") + "";
        String verifyEmail = VerifyUtil.getEmail();
        String code = VerifyUtil.getActivationCode();
        boolean flag = false;
        if (email.equals(verifyEmail) && key.equals(code)) {
            flag = true;
        }
        return flag;
    }
}


