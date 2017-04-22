package com.mello.service;

import com.mello.entity.User;

/**
 * Created by Administrator on 2017/2/26.
 * 用户行为的增删改查服务
 */
public interface UserService {
    Boolean register(User user);

    Boolean infoUpdate(User user);

    Boolean statusUpdate(Integer id, String activation, String activationCode);

    Boolean loginVerify(String email, String password);

    Boolean emailVerify(String email);

    void sendMessage(Integer index, String message);

    void sendMessage(Integer index, String message, String email);
}
