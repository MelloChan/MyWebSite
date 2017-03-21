package com.mello.dao;


import com.mello.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/2/26.
 * 用户DAO层接口 基本的CRUD
 */
public interface UserDAO {
    void insert(User user) throws SQLException;

    void update(User user) throws SQLException;

    Boolean delete(Integer id) throws SQLException;

    User search(String email, String password) throws SQLException;

    User search(String email) throws SQLException;

    List<User> userList() throws SQLException;

    List<User> userList(int start, int count) throws SQLException;
}
