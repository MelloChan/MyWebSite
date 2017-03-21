package com.mello.dao.impl;

import com.mello.dao.DataDAO;
import com.mello.entity.Data;
import com.mello.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/3/8.
 */
public class DataDAOImpl implements DataDAO {
    private static final String DBT = "userinfo";

    @Override
    public Boolean insert(Data data) throws SQLException {
        String sql = "insert into " + DBT + "(id,message,email,ip,date)values(null,?,?,?,?)";
        Connection connection = ConnectionFactory.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, data.getMessage());
        ps.setString(2, data.getEmail());
        ps.setString(3, data.getIp());
        ps.setDate(4, data.getDate());
        return ps.execute();
    }
}
