package com.mello.entity;

/**
 * Created by Administrator on 2017/4/4.
 * WX用户封装字段
 */
public class WxUser {
    private String openID;
    private String no;
    private String password;

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "WxUser{" +
                "openID='" + openID + '\'' +
                ", no='" + no + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
