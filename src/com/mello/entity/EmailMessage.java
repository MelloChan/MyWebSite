package com.mello.entity;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/30.
 */
public class EmailMessage {
    private Integer index;
    private String message;
    private String title;
    private Date date;
    private String email;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmailMessage{" +
                "index=" + index +
                ", message='" + message + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", email='" + email + '\'' +
                '}';
    }
}
