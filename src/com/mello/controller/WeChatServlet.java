package com.mello.controller;

import com.mello.dispenser.EventDispatcher;
import com.mello.dispenser.MsgDispatcher;
import com.mello.util.CheckSignature;
import com.mello.util.MsgUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/4.
 * 认证接口与消息路由
 */
@WebServlet(name = "WeChatServlet", urlPatterns = {"/api/wechat/dowith"})
public class WeChatServlet extends HttpServlet {
    private static final String TOKEN = "mello";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        try (PrintWriter pw = resp.getWriter()) {
            if (CheckSignature.check(signature, TOKEN, timestamp, nonce)) {
                pw.print(echostr);
                pw.flush();
            } else {
                System.out.println("错误请求!");
            }
        } catch (IOException e) {
            System.out.println("微信接口IO异常:"+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/xml;charset=UTF-8");
        String respXML=MsgUtil.returnRespXML(req);
        try (PrintWriter pw = resp.getWriter()) {
            pw.print(respXML);
            pw.flush();
        } catch (IOException e) {
            System.out.println("WeChat Servlet IO异常:"+e.getMessage());
            e.printStackTrace();
        }
    }

}
