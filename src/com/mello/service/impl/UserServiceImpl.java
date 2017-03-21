package com.mello.service.impl;

import com.mello.dao.UserDAO;
import com.mello.dao.impl.UserDAOImpl;
import com.mello.entity.User;
import com.mello.service.UserService;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/2/26.
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
    public Boolean InfoUpdate(User user) {
        boolean flag = false;
        try {
            userDAO.update(user);
            flag = true;
        } catch (SQLException e) {
            System.out.println("更新异常:" + e.getMessage());
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
    public Boolean LoginVerify(String email, String password) {
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
    public Boolean EmailVerify(String email) {
        User user = null;
        try {
            user = userDAO.search(email);
        } catch (Exception e) {
            System.out.println("登录异常:" + e.getMessage());
        }
        return user == null;
    }

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        System.out.println(userService.EmailVerify("35298319@qq.com"));

    }
}
