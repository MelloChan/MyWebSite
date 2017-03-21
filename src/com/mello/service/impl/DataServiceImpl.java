package com.mello.service.impl;

import com.mello.dao.impl.DataDAOImpl;
import com.mello.entity.Data;
import com.mello.service.DataService;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/3/3.
 */
public class DataServiceImpl implements DataService {
    @Override
    public Boolean doInsert(Data data) {
        boolean flag = false;
        try {
            flag = new DataDAOImpl().insert(data);
        } catch (Exception e) {
            System.out.println("插入异常:" + e.getMessage());
        }
        return flag;
    }
}
