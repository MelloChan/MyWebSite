package com.mello.util;

import java.util.Properties;

/**
 * Created by Administrator on 2017/4/20.
 * 配置文件工具类
 */
public class PropertiesUtil {

    /**
     * 读取配置 返回properties
     * @param className 类名
     * @param propertiesFileName 属性文件
     * @return 返回properties
     */
    public static Properties getProperties(Class className, String propertiesFileName){
        Properties properties=new Properties();
        try {
            properties.load(className.getClassLoader().getResourceAsStream(propertiesFileName));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}
