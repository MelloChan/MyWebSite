package com.mello.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/26.
 */
public class JsoupNetIP {
    public static void main(String[] args) {
        new Thread(new JsoupGet()).start();
    }
}
class JsoupGet implements Runnable{
    private static String url="http://www.ip.cn";
    @Override
    public void run() {
        try {
            Document document= Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0").timeout(5000).get();
            Elements elements=document.select("code");
            for (Element element:elements){
                System.out.println(element.text());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
