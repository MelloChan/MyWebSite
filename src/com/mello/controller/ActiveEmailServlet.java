package com.mello.controller;

import com.mello.service.impl.UserServiceImpl;
import com.mello.util.VerifyUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2017/3/24.
 */
@WebServlet(name = "ActiveEmailServlet", urlPatterns = {"/activeEmail"})
public class ActiveEmailServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String urlMessage = getURL(req);
        new Thread(new Runnable() {
            @Override
            public void run() {
                new UserServiceImpl().sendMessage(2, urlMessage, VerifyUtil.getEmail());
            }
        }).start();
        req.setAttribute("email", VerifyUtil.getEmail());
        req.getRequestDispatcher("/jsp/active.jsp").forward(req, resp);
    }

    private String getURL(HttpServletRequest req) throws IOException {
        String email = "email=" + VerifyUtil.getEmail();
        String activationCode = "key=" + VerifyUtil.getActivationCode();
//        Document document= Jsoup.connect("http://www.ip.cn/").userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0")
//                .timeout(5000).get();
//        Element element=document.select("code").first();
//        String ip= element.text();
        String ip=req.getLocalAddr();
        //拼接url
        StringBuilder url = new StringBuilder();
        url.append("http://");
        url.append(ip);
        url.append("/activeAccount?");
        url.append(email);
        url.append("&");
        url.append(activationCode);
        StringBuilder sb = new StringBuilder();
        sb.append("<a href=\"");
        sb.append(url.toString());
        sb.append("\" target=\"_blank\">");
        sb.append("点击激活账户</a>");
        sb.append("<br>如果无法点击，请使用以下链接激活账户:<br>");
        sb.append("<a href=\"");
        sb.append(url.toString());
        sb.append("\" target=\"_blank\">");
        sb.append(url);
        sb.append("</a>");
        return sb.toString();
    }
}
