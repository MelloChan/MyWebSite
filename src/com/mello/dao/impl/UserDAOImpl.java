package com.mello.dao.impl;

import com.mello.dao.UserDAO;
import com.mello.entity.User;
import com.mello.util.ConnectionFactory;
import com.mello.util.VerifyUtil;

import java.sql.*;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 * 用户DAO层
 */
public class UserDAOImpl implements UserDAO {
    private static final String DBT = "userinfo";

    /**
     * 注册新用户
     *
     * @param user 插入用户信息
     * @return
     * @throws SQLException
     */
    @Override
    public void insert(User user) throws SQLException {
        String sql = "insert into " + DBT + "(id,username,password,email,registrationTime,ip,activation,activationCode) " +
                "values(null,?,?,?,?,?,?,?) ";
        try(Connection connection = ConnectionFactory.getInstance()) {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setDate(4, user.getRegistrationTime());
            ps.setString(5, user.getIp());
            ps.setString(6,user.getActivation());
            ps.setString(7, user.getActivationCode());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (null != rs) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    user.setId(id);
                    VerifyUtil.setId(user.getId());
                    VerifyUtil.setEmail(user.getEmail());
                    VerifyUtil.setActivation(user.getActivation());
                    VerifyUtil.setActivationCode(user.getActivationCode());
                }
            }
        }
    }

    /**
     * 更新用户个人信息
     *
     * @param user 更新个人信息
     * @return
     * @throws SQLException
     */
    @Override
    public void update(User user) throws SQLException {
        String username = user.getUsername();
        String password = user.getPassword();
        Integer id = user.getId();
        String sql = "update " + DBT + " set username=?,password=? where id=? ";
        try( Connection connection = ConnectionFactory.getInstance()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, id);
            ps.execute();
        }
    }

    /**
     * 用户通过邮件url激活时调用
     * @param id 用户id
     * @param activation 用户状态
     * @param activationCode 用户激活码
     * @throws SQLException
     */
    @Override
    public void updateStatus(Integer id,String activation,String activationCode)throws SQLException{
        String sql="update "+DBT+" set activation=?,activationCode=? where id=? ";
        try( Connection connection=ConnectionFactory.getInstance()) {
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setString(1,activation);
            ps.setString(2,activationCode);
            ps.setInt(3,id);
            ps.execute();
        }
    }

    /**
     * 后台删除用户
     *
     * @param id 通过索引id删除指定用户
     * @return
     * @throws SQLException
     */
    @Override
    public Boolean delete(Integer id) throws SQLException {
        return null;
    }

    /**
     * 查找用户负责登录验证
     *
     * @param email    用户email
     * @param password 用户密码
     * @return 验证成功则为真
     * @throws SQLException
     */
    @Override
    public User search(String email, String password) throws SQLException {
        String sql = " select id,username,password,email,registrationTime,ip from " + DBT + " where email=? and password=? ";
        try (Connection connection = ConnectionFactory.getInstance()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            User user = null;
            while (rs.next()) {
                user = new User();
                int id = rs.getInt(1);
                user.setId(id);
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setRegistrationTime(rs.getDate(5));
                user.setIp(rs.getString(6));
                VerifyUtil.setId(rs.getInt(1));
                VerifyUtil.setEmail(user.getEmail());
                VerifyUtil.setUsername(user.getUsername());
            }
            return user;
        }
    }

    /**
     * @param email
     * @return
     * @throws SQLException
     */
    @Override
    public User search(String email) throws SQLException {
        String sql = " select username,email from " + DBT + " where email=? ";
        try(Connection connection = ConnectionFactory.getInstance()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            User user = null;
            while (rs.next()) {
                user = new User();
                user.setUsername(rs.getString(1));
                user.setEmail(rs.getString(2));
            }
            return user;
        }
    }

    /**
     * 获取用户列表
     *
     * @return 返回一个用户链表
     * @throws SQLException SQL异常
     */
    @Override
    public List<User> userList() throws SQLException {
        return null;
    }

    /**
     * 指定数量获取用户列表
     *
     * @param start 起始索引
     * @param count 终止索引
     * @return 返回一个用户链表
     * @throws SQLException SQL异常
     */
    @Override
    public List<User> userList(int start, int count) throws SQLException {
        return null;
    }
}
