package com.mello.filter;

import com.mello.listener.SessionListener;
import com.mello.util.VerifyUtil;
import org.java_websocket.WebSocketImpl;
import com.mello.controller.WebSocket;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2016/11/20.
 */
@WebFilter(filterName = "StartFilter", urlPatterns = {"/*"})
public class StartFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("StartFilter Init!");
        this.startWebSocketOnline();  //通过过滤器 先构建 调用在线人数统计~
    }

    public void startWebSocketOnline() {
        System.out.println("开始启动WebSocket");
        WebSocketImpl.DEBUG = false;
        int port = 8888;
        WebSocket s;
        try {
            s = new WebSocket(port);
            s.start();
        } catch (UnknownHostException e) {
            System.out.println("启动失败！");
            e.printStackTrace();
        }
        System.out.println("启动成功!");
    }

    @Override
    public void destroy() {
        System.out.println("StartFilter Destroy!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setCharacterEncoding("utf-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
