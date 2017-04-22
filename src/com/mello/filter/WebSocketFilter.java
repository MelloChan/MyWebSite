package com.mello.filter;

import com.mello.ws.WebSocket;
import org.java_websocket.WebSocketImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2017/4/6.
 */
@WebFilter(filterName = "WebSocketFilter",urlPatterns = {"/main/html/main.html"})
public class WebSocketFilter implements Filter{
    private static int count=0;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("WebSocket Filter Init!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(count==0){
            startWebSocket();
            count++;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("WebSocket Filter Destroy!");
    }

    private void startWebSocket(){
        WebSocket ws;
        WebSocketImpl.DEBUG=false;
        int port=8888;
        try {
            ws=new WebSocket(port);
            ws.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("WS启动成功!");
    }
}
