package com.mello.util;

import com.mello.entity.AccessToken;

import java.io.IOException;

/**
 * Created by Administrator on 2017/4/6.
 * AccessToken获取定时器 所有token需求都从这里访问获取
 */
public class AccessTokenTimer implements Runnable {
    // 第三方用户唯一凭证
    public static String appid = "";
    // 第三方用户唯一凭证密钥
    public static String appsecret = "";
    public static AccessToken accessToken = null; //唯一访问token方式

    @Override
    public void run() {
        while (true) {
            try {
                accessToken = HttpUtil.getAccessToken(appid, appsecret);
                if (null != accessToken) {
                    // 休眠7000秒
                    Thread.sleep((accessToken.getExpires_in() - 200) * 1000);
                } else {
                    // 如果access_token为null，60秒后再获取
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException el) {
                    System.out.println(e.getMessage());
                }
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
