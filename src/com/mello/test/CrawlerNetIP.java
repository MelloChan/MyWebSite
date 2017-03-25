package com.mello.test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/25.
 */
public class CrawlerNetIP {
    public static void main(String[] args) {

    }
}
class GetIP implements Runnable{
    static String url="http://www.ip.cn/";
    @Override
    public void run() {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        try {
            Response response=client.newCall(request).execute();
            response.body();
            String string=response.message();
            System.out.println(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
