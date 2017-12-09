package com.mello.dao.impl;

import com.mello.dao.WxUserDAO;
import com.mello.entity.WxUser;
import com.mello.util.ConnectionFactory;

import java.sql.*;

/**
 * Created by Administrator on 2017/4/4.
 * 微信用户CURD
 */
public class WxUserDAOImpl implements WxUserDAO {
    private static final String DBT = "wxuser";

    @Override
    public void insert(WxUser wxUser) throws SQLException {
        String sql = "insert into " + DBT + " (openID,sNumber,password) " +
                "values(?,?,?) ";
        try(Connection connection = ConnectionFactory.getInstance()) {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, wxUser.getOpenID());
            ps.setString(2, wxUser.getNo());
            ps.setString(3, wxUser.getPassword());
            ps.execute();
        }
    }

    @Override
    public void delete(String openID) throws SQLException {
        String sql="delete from "+DBT+" where openID=? ";
        try( Connection connection=ConnectionFactory.getInstance()) {
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setString(1,openID);
            ps.execute();
        }
    }

    @Override
    public WxUser search(String openID) throws SQLException {
        String sql = "select sNumber,password from " + DBT + " where openID = ? ";
        try( Connection connection = ConnectionFactory.getInstance()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,openID);
            ResultSet rs = ps.executeQuery();
            WxUser wxUser = null;
            if (rs != null) {
                while (rs.next()) {
                    wxUser = new WxUser();
                    wxUser.setOpenID(openID);
                    wxUser.setNo(rs.getString("sNumber"));
                    wxUser.setPassword(rs.getString("password"));
                }
            }
            return wxUser;
        }
    }

    @Override
    public Boolean count(String sNumber)throws SQLException{
        String sql="select count(sNumber) from "+DBT+" where sNumber=? ";
        try(Connection connection=ConnectionFactory.getInstance()) {
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setString(1,sNumber);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return rs.getInt(1) == 1;
        }
    }

    @Override
    public void update(WxUser wxUser) throws SQLException {

    }
}
