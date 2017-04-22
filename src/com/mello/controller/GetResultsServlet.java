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
 * Created by Administrator on 2017/4/5.
 * 获取考试成绩数据接口
 */
@WebServlet(name = "GetResultsServlet", urlPatterns = {"/api/jwxt/results"})
public class GetResultsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("getResults doGet");
       doPost(req,resp);
    }

    /**
     * v5kf 融合第三方接口 根据菜单栏 || 用户回复关键字 成绩、考试成绩等 POST路由至此 返回XML 成绩数据
     *
     * @param req  请求头
     * @param resp 响应头
     * @throws ServletException servlet异常
     * @throws IOException      IO流异常
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("getResults doPost");
        resp.setContentType("application/xml;charset=UTF-8");
        String wechat = req.getParameter("wechat");
        if (wechat != null) {
            String respXML = MsgUtil.returnRespXML(req);
            try (PrintWriter pw = resp.getWriter()) {
                pw.print(respXML);
                pw.flush();
            } catch (IOException e) {
                System.out.println("Get Results异常:"+e.getMessage());
                e.printStackTrace();
            }
        }
    }


}
