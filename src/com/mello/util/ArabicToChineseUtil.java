package com.mello.util;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/18.
 * 数字转换
 */
public class ArabicToChineseUtil {

    public static String arabicToChina(String arabicNum){
        Map<String,String>map=new Hashtable<>();
        map.put("1","一");
        map.put("2","二");
        map.put("3","三");
        map.put("4","四");
        map.put("5","五");
        map.put("6","六");
        map.put("7","日");
        return map.get(arabicNum);
    }
}
