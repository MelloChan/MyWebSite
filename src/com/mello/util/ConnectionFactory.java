package com.mello.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/7.
 * c3p0连接池 单例饿汉模式
 */
public class ConnectionFactory {
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();
    private static ConnectionFactory cf = new ConnectionFactory();

    private ConnectionFactory() {
    }

    public static ConnectionFactory getInstance() {
        return cf;
    }

    static {
        Properties properties = new Properties();
        try {
            properties.load(ConnectionFactory.class.getClassLoader().getResourceAsStream("dbcp.properties"));
        } catch (Exception e) {
            System.out.println("配置文件读取错误:" + e.getMessage());
        }
        String driver = properties.getProperty("driverClassName");
        String url = properties.getProperty("url");
        String user = properties.getProperty("username");
        String password = properties.getProperty("password");
        try {
            cpds.setDriverClass(driver);
            cpds.setJdbcUrl(url);
            cpds.setUser(user);
            cpds.setPassword(password);
            //设置最大连接数
            cpds.setMaxPoolSize(Integer.parseInt(properties.getProperty("max")));
            //设置最小连接数
            cpds.setMinPoolSize(Integer.parseInt(properties.getProperty("min")));
            //设置初始连接数
            cpds.setInitialPoolSize(Integer.parseInt(properties.getProperty("init")));
            //设置最大statement缓存数
            cpds.setMaxStatements(Integer.parseInt(properties.getProperty("maxStatements")));
            //等待时间
            cpds.setCheckoutTimeout(Integer.parseInt(properties.getProperty("maxTimeout")));
            //每5小时检查空闲连接
            cpds.setIdleConnectionTestPeriod(18000);

        } catch (PropertyVetoException e) {
            System.out.println("配置异常:" + e.getMessage());
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

}
