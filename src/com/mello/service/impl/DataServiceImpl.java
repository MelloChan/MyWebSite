package com.mello.service.impl;

import com.mello.dao.impl.DataDAOImpl;
import com.mello.entity.Data;
import com.mello.service.DataService;

/**
 * Created by Administrator on 2017/3/3.
 * 个人聊天室用户发送的数据 TODO
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
