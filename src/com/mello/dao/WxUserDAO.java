package com.mello.dao;

import com.mello.entity.WxUser;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/4/4.
 * 微信用户CRUD
 */
public interface WxUserDAO {

    void insert(WxUser wxUser)throws SQLException;

    void delete(String openID)throws SQLException;

    WxUser search(String openID)throws SQLException;

    Boolean count(String sNumber)throws SQLException;

    void update(WxUser wxUser)throws SQLException;

}
