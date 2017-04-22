package com.mello.ws;

import org.java_websocket.WebSocket;

import java.util.*;

public class WebSocketPool {
    private static final Map<WebSocket, String> USER_CONNECTIONS = new HashMap<>();

    /**
     * 获取用户名 * @param session
     */
    public static String getUserByKey(WebSocket conn) {

        return USER_CONNECTIONS.get(conn);
    }

    /**
     * 获取在线总数 * @param
     */
    public static int getUserCount() {

        return USER_CONNECTIONS.size();
    }

    /**
     * 获取WebSocket * @param user
     */
    public static WebSocket getWebSocketByUser(String user) {
        Set<WebSocket> keySet = USER_CONNECTIONS.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String cuser = USER_CONNECTIONS.get(conn);
                if (cuser.equals(user)) {
                    return conn;
                }
            }
        }
        return null;
    }

    /**
     * 向连接池中添加连接 * @param inbound
     */
    public static void addUser(String user, WebSocket conn) {
        USER_CONNECTIONS.put(conn, user);
        System.out.println("user:" + user);
        // 添加连接
    }

    /**
     * 获取所有的在线用户 * @return
     */
    public static Collection<String> getOnlineUser() {
        List<String> setUsers = new ArrayList<>();
        Collection<String> setUser = USER_CONNECTIONS.values();
        for (String u : setUser) {
            setUsers.add(u);
        }
        return setUsers;
    }

    /**
     * 移除连接池中的连接 * @param inbound
     */
    public static boolean removeUser(WebSocket conn) {
        if (USER_CONNECTIONS.containsKey(conn)) {
            USER_CONNECTIONS.remove(conn); // 移除连接
            return true;
        } else {
            return false;
        }
    }

    /**
     * 向特定的用户发送数据 * @param user * @param message
     */
    public static void sendMessageToUser(WebSocket conn, String message) {
        if (null != conn) {
            conn.send(message);
        }
    }

    /**
     * 向所有的用户发送消息 * @param message
     */
    public static void sendMessage(String message) {
        Set<WebSocket> keySet = USER_CONNECTIONS.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String user = USER_CONNECTIONS.get(conn);
                if (user != null) {
                    conn.send(message);
                }
            }
        }
    }
}
