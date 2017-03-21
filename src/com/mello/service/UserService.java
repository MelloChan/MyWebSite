package com.mello.service;

import com.mello.entity.User;

/**
 * Created by Administrator on 2017/2/26.
 * 用户行为的增删改查
 */
public interface UserService {
    Boolean register(User user);

    Boolean InfoUpdate(User user);

    Boolean LoginVerify(String email, String password);

    Boolean EmailVerify(String email);
}
