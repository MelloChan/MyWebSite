package com.mello.service.impl;

import com.mello.dao.UserDAO;
import com.mello.dao.impl.UserDAOImpl;
import com.mello.entity.User;
import com.mello.service.UserService;
import com.mello.util.SendEmail;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/2/26.
 * 个人聊天室用户服务类
 */
public class UserServiceImpl implements UserService {
    private static UserDAO userDAO = new UserDAOImpl();

    /**
     * 用户注册 user的信息完整由前台与控制器保证
     *
     * @param user 封装用户信息
     * @return 成功与否
     */
    @Override
    public Boolean register(User user) {
        boolean flag = false;
        try {
            userDAO.insert(user);
            flag = true;
        } catch (SQLException e) {
            System.out.println("插入异常:" + e.getMessage());
        }
        return flag;
    }

    /**
     * 用户个人信息更新 前台与控制器保证id、昵称、密码信息的完整
     *
     * @param user 封装用户信息
     * @return 成功与否
     */
    @Override
    public Boolean infoUpdate(User user) {
        boolean flag = false;
        try {
            userDAO.update(user);
            flag = true;
        } catch (SQLException e) {
            System.out.println("更新异常:" + e.getMessage());
        }
        return flag;
    }

    @Override
    public Boolean statusUpdate(Integer id, String activation, String activationCode) {
        boolean flag = false;
        try {
            userDAO.updateStatus(id, activation, activationCode);
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("更新账户状态失败:" + e.getMessage());
        }
        return flag;
    }

    /**
     * 登录验证 调用此方法若结果为真则保存用户全局id
     *
     * @param email    用户标识
     * @param password 用户密码
     * @return 成功与否
     */
    @Override
    public Boolean loginVerify(String email, String password) {
        User user = null;
        try {
            user = userDAO.search(email, password);
        } catch (Exception e) {
            System.out.println("登录异常:" + e.getMessage());
        }
        return user != null;
    }

    /**
     * 验证注册邮箱是否唯一
     *
     * @param email 用户唯一邮箱
     * @return 成功与否
     */
    @Override
    public Boolean emailVerify(String email) {
        User user = null;
        try {
            user = userDAO.search(email);
        } catch (Exception e) {
            System.out.println("登录异常:" + e.getMessage());
        }
        return user == null;
    }

    /**
     * 发送访问者留言
     *
     * @return 是否正确发送
     */
    @Override
    public void sendMessage(Integer index, String message) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        SendEmail.sendMailByText(index, message);
                    } catch (GeneralSecurityException | MessagingException e) {
                        e.printStackTrace();
                        System.out.println("发送邮件异常:" + e.getMessage());
                    }
                }
            }).start();
    }

    /**
     * 提供邮件激活账户
     *
     * @param index   标题索引
     * @param message 发送的信息
     * @param email   接收者
     */
    @Override
    public void sendMessage(Integer index, String message, String email) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SendEmail.sendMailByHtml(index, message, email);
                } catch (GeneralSecurityException | MessagingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
