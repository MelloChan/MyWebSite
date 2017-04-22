package com.mello.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mello.dao.WxUserDAO;
import com.mello.dao.impl.WxUserDAOImpl;
import com.mello.entity.WxUser;
import com.mello.service.WxUserService;
import com.mello.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/4.
 * 学生绑定服务类
 */
public class WxUserServiceImpl implements WxUserService {
    private static WxUserDAO wxUserDAO = new WxUserDAOImpl();

    /**
     * 用户绑定 先post教务系统，若请求成功则插入数据库
     *
     * @param encoded 请求的表单数据
     * @param wxUser  封装好的待插入数据
     * @return 成功与否
     */
    @Override
    public boolean bindByNo(String encoded, WxUser wxUser) {
        boolean flag = false;
        try {
            if(HttpUtil.post(encoded)!=null){
                wxUserDAO.insert(wxUser);
                flag = true;
            }
        } catch (IOException e) {
            System.out.println("用户绑定异常:" + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("插入异常:" + e.getMessage());
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 学号值唯一 以此查询用户是否重复绑定
     * @param sNumber 学号
     * @return 重复绑定返回true 否则为假
     */
    @Override
    public boolean hasWxUser(String sNumber){
        boolean flag=false;
        try {
            flag = wxUserDAO.count(sNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 用户解除绑定
     *
     * @param openID 用户openid
     * @return 删除成功与否
     */
    @Override
    public boolean removeByID(String openID) {
        boolean flag = false;
        try {
            wxUserDAO.delete(openID);
            flag = true;
        } catch (SQLException e) {
            System.out.println("删除异常:" + e.getMessage());
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 返回成绩数据给前端 JSON格式
     *
     * @param encoded 加密好的学号与密码
     * @return 返回json
     */
    @Override
    public List<JsonObject> returnResult(String encoded) throws IOException {
        String cookie = HttpUtil.post(encoded);
        String html = "";
        Map<String, String> map = new HashMap<>();
        map.put("Cookie", cookie);
        try {
            html = HttpUtil.getJWXTData("http://e.zhbit.com/jsxsd/kscj/cjcx_list", map, "GET");
        } catch (IOException e) {
            System.out.println("请求页面异常:" + e.getMessage());
        }
        Document document = Jsoup.parse(html);
        Elements headers = document.select("th");
        Elements values = document.getElementById("dataList").select("tr");
        List<JsonObject> jsonArray = new ArrayList<>();
        for (int row = 1, rows = values.size(); row < rows; row++) {
            jsonArray.add(new JsonObject());
            for (int column = 0, columns = headers.size(); column < columns; column++) {
                String key = headers.get(column).text();
                Elements elements = values.get(row).select("td");
                if (elements.text().equals("未查询到数据")) {
                    jsonArray.get(row - 1).addProperty("成绩", "未查询到数据");
                    break;
                }
                String value = elements.get(column).text();
                jsonArray.get(row - 1).addProperty(key, value);
            }
        }
        return jsonArray;
    }

    /**
     * 返回学生考试时间数据给前端 JSON格式
     *
     * @param encoded 加密好的学号与密码
     * @return JSON
     */
    @Override
    public List<JsonObject> returnExam(String encoded) throws IOException {
        String cookie = HttpUtil.post(encoded);
        String html = "";
        Map<String, String> map = new HashMap<>();
        map.put("Cookie", cookie);
        try {
            html = HttpUtil.getJWXTData("http://e.zhbit.com/jsxsd/xsks/xsksap_list", map, "POST");
        } catch (IOException e) {
            System.out.println("请求页面异常:" + e.getMessage());
        }
        Document document = Jsoup.parse(html);
        Elements headers = document.select("th");
        Elements values = document.getElementById("dataList").select("tr");
        List<JsonObject> jsonArray = new ArrayList<>();
        for (int row = 1, rows = values.size(); row < rows; row++) {
            jsonArray.add(new JsonObject());
            for (int column = 0, columns = headers.size(); column < columns; column++) {
                String key = headers.get(column).text();
                Elements elements = values.get(row).select("td");
                if (elements.text().equals("未查询到数据")) {
                    jsonArray.get(row - 1).addProperty("考试时间", "未查到数据");
                    break;
                }
                String value = elements.get(column).text();
                jsonArray.get(row - 1).addProperty(key, value);
            }
        }
        return jsonArray;
    }

    /**
     * 返回学生课表数据给前端 JSON格式
     *
     * @return JSON格式
     */
    @Override
    public List<JsonObject> returnCourse(String encoded) throws IOException {
        String cookie = HttpUtil.post(encoded);
        String html = "";
        Map<String, String> map = new HashMap<>();
        map.put("Cookie", cookie);
        try {
            html = HttpUtil.getJWXTData("http://e.zhbit.com/jsxsd/xskb/xskb_list.do", map, "GET");
        } catch (IOException e) {
            System.out.println("请求页面异常:" + e.getMessage());
        }
        Document document = Jsoup.parse(html);
        Elements th = document.select("th");
        Elements td = document.getElementById("kbtable").select("td");
        List<JsonObject> jsonArray = new ArrayList<>();
        for (int i = 0, weekday = 1, time = 8; i < 50; i++) {
            jsonArray.add(new JsonObject());
            jsonArray.get(i).addProperty("weekday", weekday + "");
            jsonArray.get(i).addProperty("time", th.get(time).text());
            weekday++;
            if (weekday == 8) {
                weekday = 1;
                time++;
                if (time == 16) break;
            }
        }
        for (int i = 0; i < 50; i++) {
            String[] data = td.get(i).text().split(" ");
            if (data.length < 8) {
                String value = "";
                if (i == 49) value = td.get(i).text();
                jsonArray.get(i).addProperty("class", value);
            } else {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", data[0]);
                jsonObject.addProperty("weeks", data[1]);
                jsonObject.addProperty("place", data[2]);
                jsonObject.addProperty("teacher", data[4]);
                jsonObject.addProperty("section", data[6]);
                String classData = jsonObject.toString();
                jsonArray.get(i).addProperty("class", classData);
            }

        }
        return jsonArray;
    }
}
