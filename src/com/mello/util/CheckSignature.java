package com.mello.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/4/4.
 * 微信token认证
 */
public class CheckSignature {
    private CheckSignature(){}
    /**
     * 对微信认证进行加密比对
     * @param signature 微信检验
     * @param token 个人token
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @return 校验比对正确与否
     */
    public static boolean check(String signature,String token,String timestamp,String nonce){
        List<String>list=new ArrayList<>();
        list.add(token);
        list.add(timestamp);
        list.add(nonce);
        //按照字典序排序
        Collections.sort(list);
        return signature.equals(DigestUtil.digestSHA1(list));
    }
}
