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
 * 获取考试时间数据接口
 */
@WebServlet(name = "GetExamServlet", urlPatterns = {"/api/jwxt/exam"})
public class GetExamServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("getExam doGet!");
        doPost(req,resp);
    }

    /**
     *  v5kf 融合第三方接口 根据菜单栏 || 用户回复关键字 考试时间、考试等 POST路由至此 将封装好的XML 考试时间数据返回
     * @param req 请求头
     * @param resp 响应头
     * @throws ServletException servlet异常
     * @throws IOException IO流异常
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("getExam doPost");
        resp.setContentType("application/xml;charset=UTF-8");
        String wechat=req.getParameter("wechat");
        if(wechat!=null){
            String respXML=MsgUtil.returnRespXML(req);
            try (PrintWriter pw = resp.getWriter()) {
                pw.print(respXML);
                pw.flush();
            } catch (IOException e) {
                System.out.println("GetExam Servlet 异常:"+e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
