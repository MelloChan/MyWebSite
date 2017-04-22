package com.mello.controller;


import com.mello.util.MsgUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/4/6.
 * 获取课程表数据接口
 */
@WebServlet(name = "GetCourseServlet", urlPatterns = {"/api/jwxt/course"})
public class GetCourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("getCourse doGet!");
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("getCourse doPost!");
        resp.setContentType("application/xml;charset=UTF-8");
        String wechat = req.getParameter("wechat");
        if (wechat != null) {
            String respXML = MsgUtil.returnRespXML(req);
            try (PrintWriter pw = resp.getWriter()) {
                pw.print(respXML);
                pw.flush();
            } catch (IOException e) {
                System.out.println("getCourse 异常:"+e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
