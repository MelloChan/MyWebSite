package com.mello.controller;

import com.mello.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/3/4.
 */
@WebServlet(name = "GetMessageServlet", urlPatterns = {"/message"})
public class GetMessageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ip = req.getRemoteAddr();
        String message = req.getParameter("message");
        if (message != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new UserServiceImpl().sendMessage(1, ip + ":" + message);
                }
            }).start();
            req.getRequestDispatcher("/index.html").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
