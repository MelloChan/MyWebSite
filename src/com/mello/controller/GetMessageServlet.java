package com.mello.controller;

import com.mello.util.SendEmail;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

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
            try {
                SendEmail.sendMail(ip + ":" + message);
                req.getRequestDispatcher("/html/error.html").forward(req, resp);
            } catch (GeneralSecurityException | MessagingException e) {
                System.out.println("email:" + e.toString());
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
