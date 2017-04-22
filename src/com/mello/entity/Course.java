package com.mello.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/20.
 */
public class Course {

    /**
     * weekday : 1
     * time : 第一二节 
     * class : {"name":"计算机多媒体技术","weeks":"1-17(周)","place":"MD302","teacher":"张霄","section":"[01-02节]"}
     */

    private String weekday;
    private String time;
    @SerializedName("class")
    private String classX;

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }
}
