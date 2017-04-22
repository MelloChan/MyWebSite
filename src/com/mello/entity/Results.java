package com.mello.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/20.
 * 成绩实体类
 */
public class Results {

    /**
     * 序号 : 1
     * 开课学期 : 2015-2016-1
     * 课程编号 : 02120004
     * 课程名称 : C语言程序设计
     * 成绩 : 94
     * 学分 : 3
     * 总学时 :
     * 考核方式 :
     * 课程属性 : 必修
     * 课程性质 : 学科基础课
     */

    @SerializedName("序号")
    private String index;
    @SerializedName("开课学期")
    private String time;
    @SerializedName("课程编号")
    private String number;
    @SerializedName("课程名称")
    private String cName;
    @SerializedName("成绩")
    private String result;
    @SerializedName("学分")
    private String credit;
    @SerializedName("总学时")
    private String totalHour;
    @SerializedName("考核方式")
    private String way;
    @SerializedName("课程属性")
    private String property;
    @SerializedName("课程性质")
    private String nature;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(String totalHour) {
        this.totalHour = totalHour;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }
}
