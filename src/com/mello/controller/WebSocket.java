package com.mello.controller;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class WebSocket extends WebSocketServer {
    private int j = 0;
    private int h = 0;
    private int e = 0; //异常记录
    private int l = 0;
    private static int count = -1;

    public WebSocket(InetSocketAddress address) {
        super(address);
        System.out.println("地址" + address);
    }

    public WebSocket(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
        System.out.println("端口" + port);
    }

    /**
     * 触发关闭事件
     */
    @Override
    public void onClose(org.java_websocket.WebSocket conn, int message, String reason, boolean remote) {
        System.out.println("Socket关闭:" + reason);
        userLeave(conn);
        count--;
    }

    /**
     * 触发异常事件
     */
    @Override
    public void onError(org.java_websocket.WebSocket conn, Exception message) {
        System.out.println("Socket异常:" + message.toString());
        e++;
    }

    /**
     * 客户端发送消息到服务器时触发事件
     */
    @Override
    public void onMessage(org.java_websocket.WebSocket conn, String message) {
        System.out.println("Socket接收:" + message);
        if (message != null) {
            //处理是否将用户加入
            this.userjoin(message, conn);
            System.out.println("发送:" + conn + " 消息为:" + message);
            //TODO 将用户发送的信息存储到DB
        }
    }

    /**
     * 触发连接事件
     */
    @Override
    public void onOpen(org.java_websocket.WebSocket conn, ClientHandshake handshake) {
        System.out.println("有人连接Socket conn:" + conn);
        l++;
        count++;
        System.out.println("当前人数:" + count);
    }

    /**
     * 用户加入处理 * @param user
     */
    public void userjoin(String user, org.java_websocket.WebSocket conn) {
        boolean flag = user.contains(":");
        if (!flag) {
            String joinMsg = "[用户]" + user + "上线了！";
            WebSocketPool.addUser(user, conn);
            WebSocketPool.sendMessage(joinMsg);
        } else {
            WebSocketPool.sendMessage(user);
        }
    }

    /**
     * 用户下线处理 * @param user
     */
    public void userLeave(org.java_websocket.WebSocket conn) {
        String user = WebSocketPool.getUserByKey(conn);
        boolean b = WebSocketPool.removeUser(conn); // 在连接池中移除连接
        if (b) {
            WebSocketPool.sendMessage(user); // 把当前用户从所有在线用户列表中删除
            String joinMsg = "[用户]" + user + "下线了！";
            WebSocketPool.sendMessage(joinMsg);

// 向在线用户发送当前用户退出的消息
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("开始启动WebSocket");
        WebSocketImpl.DEBUG = false;
        int port = 8888;
        // 端口随便设置，只要不跟现有端口重复就可以
        WebSocket s = null;
        try {
            s = new WebSocket(port);
        } catch (UnknownHostException e) {
            System.out.println("启动WebSocket失败！");
            e.printStackTrace();
        }
        assert s != null;
        s.start();
        System.out.println("启动WebSocket成功！");

    }

}
