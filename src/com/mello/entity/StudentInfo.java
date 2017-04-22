package com.mello.entity;

/**
 * Created by Administrator on 2017/4/1.
 */
public class StudentInfo {
    private int id;
    private String sNo;
    private String sName;
    private String sSex;
    private String sBirthday;
    private String sTel;
    private String sDepartments;
    private String sProfessional;
    private String sClass;
    private String sPolitical;
    private String sNative;
    private String sNational;
    private String sAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsSex() {
        return sSex;
    }

    public void setsSex(String sSex) {
        this.sSex = sSex;
    }

    public String getsBirthday() {
        return sBirthday;
    }

    public void setsBirthday(String sBirthday) {
        this.sBirthday = sBirthday;
    }

    public String getsTel() {
        return sTel;
    }

    public void setsTel(String sTel) {
        this.sTel = sTel;
    }

    public String getsDepartments() {
        return sDepartments;
    }

    public void setsDepartments(String sDepartments) {
        this.sDepartments = sDepartments;
    }

    public String getsProfessional() {
        return sProfessional;
    }

    public void setsProfessional(String sProfessional) {
        this.sProfessional = sProfessional;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getsPolitical() {
        return sPolitical;
    }

    public void setsPolitical(String sPolitical) {
        this.sPolitical = sPolitical;
    }

    public String getsNative() {
        return sNative;
    }

    public void setsNative(String sNative) {
        this.sNative = sNative;
    }

    public String getsNational() {
        return sNational;
    }

    public void setsNational(String sNational) {
        this.sNational = sNational;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "id=" + id +
                ", sNo='" + sNo + '\'' +
                ", sName='" + sName + '\'' +
                ", sSex='" + sSex + '\'' +
                ", sBirthday='" + sBirthday + '\'' +
                ", sTel='" + sTel + '\'' +
                ", sDepartments='" + sDepartments + '\'' +
                ", sProfessional='" + sProfessional + '\'' +
                ", sClass='" + sClass + '\'' +
                ", sPolitical='" + sPolitical + '\'' +
                ", sNative='" + sNative + '\'' +
                ", sNational='" + sNational + '\'' +
                ", sAddress='" + sAddress + '\'' +
                '}';
    }
}
