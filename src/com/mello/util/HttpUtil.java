package com.mello.util;


import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mello.entity.AccessToken;

import okhttp3.*;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/4/2.
 * http工具类封装[单例]
 */
public class HttpUtil {
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    //    private static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
//    private static final String BASE_URL = "https://api.weixin.qq.com";
    private static volatile HttpUtil httpUtil;
    //    private static String cookie;
//    private static String respBody;
    private static final int TYPE_GET = 0;
    private static final int TYPE_POST_JSON = 1;
    private static final int TYPE_POST_FORM = 2;
    private OkHttpClient client;
//    private Handler handler;

    private HttpUtil() {
        client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public static HttpUtil getInstance() {
        HttpUtil hU = httpUtil;
        if (hU == null) {
            synchronized (HttpUtil.class) {
                if (hU == null) {
                    hU = new HttpUtil();
                    httpUtil = hU;
                }
            }
        }
        return httpUtil;
    }

    /**
     * @param url
     * @param requestType
     * @param params
     * @param json
     * @return
     */
    public String requestBySyn(String url, int requestType, Map<String, String> params, String json) {
        String respBody;
        switch (requestType) {
            case TYPE_GET:
                respBody = requestGet(url, params);
                break;
            case TYPE_POST_JSON:
                respBody = requestPostByJson(url, json);
                break;
            case TYPE_POST_FORM:
                respBody = requestPostByForm(url, params);
                break;
            default:
                respBody = "{state:failure}";
                break;
        }
        return respBody;
    }

    /**
     * 带参数的get请求
     *
     * @param url    请求的标识符
     * @param params 参数
     */
    private String requestGet(String url, Map<String, String> params) {
        String respBody = "{state:failure}";
        try {
            String string = addParams(params);
            //补全地址
            String requestUrl = String.format("%s?%s", url, string);
            Request request = addHeaders().url(requestUrl).build();
            Response response = client.newCall(request).execute();
            respBody = response.body().string();
        } catch (IOException e) {
            System.out.println("GET请求异常:" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println(respBody);
        return respBody;
    }

    /**
     * 带Json参数的post请求
     *
     * @param url  请求的标识符
     * @param json 参数
     */
    private String requestPostByJson(String url, String json) {
        String respBody = "{state:failure}";
        try {
//            String string = addParams(params);
            String requestUrl = String.format("%s", url);
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);
            Request request = addHeaders().url(requestUrl).post(requestBody).build();
            Response response = client.newCall(request).execute();
            respBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respBody;
    }

    private String requestPostByForm(String url, Map<String, String> params) {
        String respBody = "{state:failure}";
        try {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
            RequestBody requestBody = builder.build();
            String requestUrl = String.format("%s", url);
            Request request = addHeaders().url(requestUrl).post(requestBody).build();
            Response response = client.newCall(request).execute();
            respBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respBody;
    }

    /**
     * 补全参数
     *
     * @param params 参数列表
     * @return 返回完整参数
     * @throws UnsupportedEncodingException 编码类型不支持
     */
    private String addParams(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        int pos = 0;
        for (String key : params.keySet()) {
            if (pos > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s", key, URLEncoder.encode(params.get(key), "UTF-8")));
            pos++;
        }
        return sb.toString();
    }

    /**
     * 统一为请求添加头信息
     *
     * @return 返回请求头信息
     */
    private Request.Builder addHeaders() {
        return new Request.Builder()
                .addHeader("Connection", "keep-alive");
    }

//    public static String returnBody(){
//        return respBody;
//    }

    /**
     * 获取access_token封装对象 仅供包内部获取
     *
     * @return 返回封装好的 AccessToken对象
     * @throws IOException IO异常
     */
    static AccessToken getAccessToken(String appID, String appSercet) throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appID + "&secret=" + appSercet;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();
        Gson gson = new Gson();
        return gson.fromJson(json, AccessToken.class);
    }

    /**
     * 为jwxt而写的登录post
     *
     * @param encoded 加密后的post数据
     * @return 返回登录后获取的cookie
     * @throws IOException IO异常
     */
    public static String post(String encoded) throws IOException {
        String login = "http://e.zhbit.com/jsxsd/xk/LoginToXk";
        HttpClient client = HttpClientBuilder.create().build();
        List<NameValuePair> encode = new ArrayList<>();
        encode.add(new BasicNameValuePair("encoded", encoded));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(encode, "UTF-8");
        HttpPost post = new HttpPost(login);
        post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0");
        post.setHeader("Referer", "http://e.zhbit.com/jsxsd/");
        post.setEntity(formEntity);
        HttpResponse response = client.execute(post);
        String cookie;
        if("302".equals(String.valueOf(response.getStatusLine().getStatusCode()))){
          String set_Cookie = response.getFirstHeader("Set-Cookie").getValue();
            cookie=set_Cookie.substring(0, set_Cookie.indexOf(";"));
        }else{
            cookie=null;
        }
        return cookie;
    }

    /**
     * 教务系统数据请求
     *
     * @param url    请求的地址(成绩、考试时间、课表...)
     * @param map    参数列表
     * @param method 请求方法
     * @return 响应页面
     * @throws IOException 抛出请求异常
     */
    public static String getJWXTData(String url, Map<String, String> map, String method) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = null;
        String cookie = map.get("Cookie");
        switch (method) {
            case "GET":
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Cookie", cookie)
                        .get()
                        .build();
                break;
            case "POST":
                //当前年份
                String nowYear = TimeUtil.nowYear();
                //当前学期【1 or 2】
                String semester = TimeUtil.nowSemester();
                //当前具体学期【2016-2017-1 or 2016-2017-2】
                String now = String.valueOf(Integer.parseInt(nowYear) - 1) +
                        "-" +
                        TimeUtil.nowYear() +
                        "-" +
                        semester;
                RequestBody requestBody = new FormBody.Builder().add("xnxqid", now).build();
                request = new Request.Builder().url(url)
                        .addHeader("Cookie", cookie)
                        .post(requestBody)
                        .build();
                break;
            default:
                break;
        }
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


}
