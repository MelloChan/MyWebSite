package com.mello.dao;

import com.mello.entity.Data;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/3/8.
 */
public interface DataDAO {
    Boolean insert(Data data) throws SQLException;
}
