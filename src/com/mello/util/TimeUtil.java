package com.mello.util;



import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/16.
 * 获取本地时间类工具
 */
public class TimeUtil {
    /**
     * 当前时间
     *
     * @return 返回当前时间类
     */
    public static LocalDate now() {
        return LocalDate.now();
    }

    /**
     * 获取当前年-月-日
     *
     * @return 返回年月日字符串
     */
    public static String nowTime() {
        return now().toString();
    }

    /**
     * 获取当前年份
     *
     * @return 返回当前年份字符串
     */
    public static String nowYear() {
        return String.valueOf(now().getYear());
    }

    /**
     * 获取当前月份
     *
     * @return 返回当前月份字符串
     */
    public static String nowMonth() {
        return String.valueOf(now().getMonthValue());
    }

    /**
     * 获取当前天数
     *
     * @return 返回当前天数字符串
     */
    public static String nowDay() {
        return String.valueOf(now().getDayOfMonth());
    }

    /**
     * 返回整型 星期
     * @param day 英文格式星期
     * @return 1 ....
     */
    public static String getDayOfWeek(String day){
        Map<String,String>days=new HashMap<>();
        days.put("MONDAY","1");
        days.put("TUESDAY","2");
        days.put("WEDNESDAY","3");
        days.put("THURSDAY","4");
        days.put("FRIDAY","5");
        days.put("SATURDAY","6");
        days.put("SUNDAY","7");
        return days.get(day);
    }

    /**
     * 返回当前学期 在3月~9月 属于第二学期  9月~12月、1月、2月属于第一学期
     *
     * @return 返回当前学期
     */
    public static String nowSemester() {
        int month = Integer.parseInt(nowMonth());
        String semester;
        if (month <= 9 && month >= 3) {
            semester = "2";
        } else {
            semester = "1";
        }
        return semester;
    }

    /**
     * 返回当前学期 在3月~9月 属于第二学期  9月~12月、1月、2月属于第一学期
     *
     * @return 返回上学期
     */
    public static String lastSemester() {
        int month = Integer.parseInt(nowMonth());
        String semester;
        if (month <= 9 && month >= 3) {
            semester = "1";
        } else {
            semester = "2";
        }
        return semester;
    }

    /**
     * 获取当前开学周次
     * @return 返回当前周次[如:1,2,3,```
     */
    public static int getWeeks(){
        Properties properties =PropertiesUtil.getProperties(TimeUtil.class,"termBegins.properties");
        int year=Integer.parseInt(properties.getProperty("year"));
        int month=Integer.parseInt(properties.getProperty("month"));
        int day=Integer.parseInt(properties.getProperty("day"));
        LocalDate beginDate=LocalDate.of(year,month,day);
        LocalDate endDate=now();
        int between= Math.toIntExact(ChronoUnit.DAYS.between(beginDate, endDate));
        int weeks=between/7;
        float mod=between%7;
        return mod>0?weeks+1:weeks;
    }
}
