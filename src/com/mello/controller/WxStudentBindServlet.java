package com.mello.controller;


import com.mello.entity.WxUser;
import com.mello.service.WxUserService;
import com.mello.service.impl.WxUserServiceImpl;
import com.mello.util.DigestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/4/5.
 * 微信学生绑定账户接口[融合v5kf]
 */
@WebServlet(name = "WxStudentBindServlet", urlPatterns = {"/api/jwxt/studentBind"})
public class WxStudentBindServlet extends HttpServlet {

    /**
     * 获取用户的openid、学号、加密后的密码封装插入DB，返回JSON数据 SUCCESS 或 FAILURE
     *
     * @param req  请求头
     * @param resp 响应头
     * @throws ServletException servlet异常
     * @throws IOException      IO流异常
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("WXBind doPost");
        resp.setContentType("text/plain;charset=UTF-8");
        String wechat = req.getParameter("wechat");
        System.out.println(wechat);
        if (wechat != null) {
            String openid = req.getParameter("openid");
            req.getSession().setAttribute("openid", openid);
            req.getRequestDispatcher("/jwxt/function/login.html").forward(req, resp);
        } else {
            try (PrintWriter pw = resp.getWriter()) {
                String msg;
                msg = !hasWxUser(req) ? (verifyWxUser(req) ? "SUCCESS" : "FAILURE") : "ERROR";
                pw.print(msg);
                pw.flush();
            } catch (IOException e) {
                System.out.println("BindServlet 异常: "+e.getMessage());
                e.printStackTrace();
            }
        }

    }

    /**
     * v5kf 融合第三方接口 v5kf GET请求到此 接收openid并存入session
     *
     * @param req  请求头
     * @param resp 响应头
     * @throws ServletException servlet异常
     * @throws IOException      IO流异常
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("WXBind doGet");
        doPost(req, resp);
    }

    /**
     * 封装用户数据，传入服务类接口方法
     *
     * @param req 请求头
     * @return 是否绑定成功【失败：用户的账户或密码有误】
     */
    private boolean verifyWxUser(HttpServletRequest req) {
        String no = req.getParameter("number");
        String password = req.getParameter("password");
        String openid = (String) req.getSession().getAttribute("openid");
        if (no == null || password == null || openid == null) {
            return false;
        }
        WxUser wxUser = new WxUser();
        wxUser.setNo(no);
        wxUser.setPassword(DigestUtil.digestBase64(password));
        wxUser.setOpenID(openid);
        String encoded = DigestUtil.digestBase64(no) + "%%%" + DigestUtil.digestBase64(password);
        WxUserService wxs = new WxUserServiceImpl();
        return wxs.bindByNo(encoded, wxUser);
    }

    /**
     * 是否重复绑定
     *
     * @param req 请求头
     * @return 若重复绑定返回true 否则 false
     */
    private boolean hasWxUser(HttpServletRequest req) {
        String no = req.getParameter("number");
        return no != null && new WxUserServiceImpl().hasWxUser(no);
    }
}
