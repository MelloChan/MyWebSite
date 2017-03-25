package com.mello.util;


/**
 * Created by Administrator on 2017/3/9.
 * 创建登录用户的全局ID
 */
public class VerifyUtil {
    private static Integer id;
    private static String email;
    private static String username;
    private static String activation;
    private static String activationCode;

    public static void setId(Integer id) {
        VerifyUtil.id = id;
    }

    public static Integer getId() {
        return id;
    }

    public static void setEmail(String email) {
        VerifyUtil.email = email;
    }

    public static String getEmail() {
        return email;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        VerifyUtil.username = username;
    }

    public static String getActivation() {
        return activation;
    }

    public static void setActivation(String activation) {
        VerifyUtil.activation = activation;
    }

    public static String getActivationCode() {
        return activationCode;
    }

    public static void setActivationCode(String activationCode) {
        VerifyUtil.activationCode = activationCode;
    }
}
