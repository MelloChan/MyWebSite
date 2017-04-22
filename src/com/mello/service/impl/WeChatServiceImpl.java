package com.mello.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mello.dao.impl.WxUserDAOImpl;
import com.mello.entity.WxUser;
import com.mello.entity.message.resp.Article;
import com.mello.entity.message.resp.NewsMessage;
import com.mello.entity.message.resp.TextMessage;
import com.mello.service.WeChatService;
import com.mello.service.WxUserService;
import com.mello.util.ArabicToChineseUtil;
import com.mello.util.DigestUtil;
import com.mello.util.MsgUtil;
import com.mello.util.TimeUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/5.
 * 响应用户信息服务类
 */
public class WeChatServiceImpl implements WeChatService {

    /**
     * 封装文本回复基本信息
     *
     * @param map 参数
     * @return 文本类
     */
    private TextMessage getTextMessage(Map<String, String> map) {
        TextMessage text = new TextMessage();
        text.setFromUserName(map.get("ToUserName"));
        text.setToUserName(map.get("FromUserName"));
        text.setMsgType(MsgUtil.RESP_TYPE_TEXT);
        text.setFuncFlag(0);
        text.setCreateTime(new Date().getTime());
        return text;
    }

    /**
     * 封装图文回复基本信息
     *
     * @param map 参数
     * @return 图文消息类
     */
    private NewsMessage getNewsMessage(Map<String, String> map) {
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setFromUserName(map.get("ToUserName"));
        newsMessage.setToUserName(map.get("FromUserName"));
        newsMessage.setMsgType(MsgUtil.RESP_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        newsMessage.setCreateTime(new Date().getTime());
        return newsMessage;
    }

    /**
     * 文本信息响应
     *
     * @param map 解析的用户请求的信息
     * @return 返回封装好的XML
     */
    @Override
    public String textType(Map<String, String> map) throws IOException {
        TextMessage text = getTextMessage(map);
        String content = "";
        //获取用户openid
        String id = map.get("FromUserName");
        WxUser wxUser = null;
        try {
            //根据id查找用户
            wxUser = new WxUserDAOImpl().search(id);
        } catch (SQLException e) {
            System.out.println("查找异常:" + e.getMessage());
            e.printStackTrace();
        }
        //用户回复关键字
        String key = map.get("Content");
        if (wxUser != null) {
            String encoded = DigestUtil.digestBase64(wxUser.getNo()) + "%%%" + wxUser.getPassword();
            switch (key) {
                case "成绩":
                case "我的成绩":
                    text.setContent(getData("成绩", encoded));
                    content = MsgUtil.textToXML(text);
                    break;
                case "考试时间":
                case "考试":
                    text.setContent(getData("考试", encoded));
                    content = MsgUtil.textToXML(text);
                    break;
                case "课表":
                case "课程表":
                    text.setContent(getData("课表", encoded));
                    content = MsgUtil.textToXML(text);
                    break;
                case "解绑":
                    if (new WxUserServiceImpl().removeByID(id)) {
                        text.setContent("解绑成功。");
                        content = MsgUtil.textToXML(text);
                    } else {
                        text.setContent("系统出错，解绑失败。");
                        content = MsgUtil.textToXML(text);
                    }
                    break;
                default:
                    break;
            }
        } else {
            text.setContent("请先绑定你的教务信息。");
            content = MsgUtil.textToXML(text);
        }

        return content;
    }

    @Override
    public String imgType(Map<String, String> map) {
        return null;
    }

    @Override
    public String linkType(Map<String, String> map) {
        return null;
    }

    @Override
    public String locationType(Map<String, String> map) {
        return null;
    }

    @Override
    public String voiceType(Map<String, String> map) {
        return null;
    }

    @Override
    public String videoType(Map<String, String> map) {
        return null;
    }

    @Override
    public String subscribeType(Map<String, String> map) {
        return null;
    }

    /**
     * 响应教务功能菜单点击事件
     *
     * @param map 参数
     * @return 返回封装好的xml
     */
    @Override
    public String clickType(Map<String, String> map) throws IOException {
        TextMessage text = getTextMessage(map);
        String content = "";
        WxUser wxUser = null;
        String id = map.get("FromUserName");
        try {
            wxUser = new WxUserDAOImpl().search(id);
        } catch (SQLException e) {
            System.out.println("查找异常:" + e.getMessage());
            e.printStackTrace();
        }
        //获取点击的菜单事件
        String eventKey = map.get("EventKey");
        if (wxUser != null) {
            String encoded = DigestUtil.digestBase64(wxUser.getNo()) + "%%%" + wxUser.getPassword();
            switch (eventKey) {
                case "成绩":
                    text.setContent(getData("成绩", encoded));
                    content = MsgUtil.textToXML(text);
//                    return MsgUtil.newsToXML(newsMessage);
                    break;
                case "考试":
                    text.setContent(getData("考试", encoded));
                    content = MsgUtil.textToXML(text);
                    break;
                case "课表":
                    text.setContent(getData("课表", encoded));
                    content = MsgUtil.textToXML(text);
                    break;
                default:
                    break;
            }
        } else {
            text.setContent("请先绑定你的教务信息。");
            content = MsgUtil.textToXML(text);
        }
//        text.setMsgType("text");
//        text.setContent(content);
//        text.setFuncFlag(0);
//        return MsgUtil.textToXML(text);
        return content;
    }

    @Override
    public String scanType(Map<String, String> map) {
        return null;
    }

    /**
     * 指定关键字类型 返回相应string数据
     *
     * @param type    回复类型
     * @param encoded 用户学号与密码的Base64加密组合
     * @return 响应数据
     * @throws IOException IO异常
     */
    private String getData(String type, String encoded) throws IOException {
        String content = "";
        WxUserService wus = new WxUserServiceImpl();
        switch (type) {
            case "考试":
                content = getExam(wus, encoded);
                break;
            case "成绩":
                content = getResults(wus, encoded);
                break;
            case "课表":
                content = getCourse(wus, encoded);
                break;
        }
        return content;
    }

    /**
     * 返回组装好的考试时间字符串
     *
     * @param wus     用户服务类 获取考试时间json数据列表
     * @param encoded 用户学号与密码的Base64加密组合
     * @return 返回组装好的字符串
     * @throws IOException IO流异常
     */
    private String getExam(WxUserService wus, String encoded) throws IOException {
        StringBuilder content = new StringBuilder();
        List<JsonObject> examList = wus.returnExam(encoded);
        content.append("你的考试\n----------------\n");
        if (!("未查到数据".equals(examList.get(0).get("考试时间").getAsString()))) {
            for (JsonObject jsonObject : examList) {
                content.append("课程:");
                content.append(jsonObject.get("课程名称").getAsString());
                content.append("\n时间:");
                content.append(jsonObject.get("考试时间").getAsString());
                content.append("\n考场:");
                content.append(jsonObject.get("考场").getAsString());
                content.append("\n座位号:");
                content.append(jsonObject.get("座位号").getAsString());
                content.append("\n------\n");
            }
            content.append("----------------\n");
            content.append("备注:临近考试考场可能变更,请考试前再次查询.");
        } else {
            content.append("未查询到考试时间~");
        }
        return content.toString();
    }

    /**
     * 返回组装好的成绩字符串
     *
     * @param wus     用户服务类 获取考试时间json数据列表
     * @param encoded 用户学号与密码的Base64加密组合
     * @return 返回组装好的字符串
     * @throws IOException IO流异常
     */
    private String getResults(WxUserService wus, String encoded) throws IOException {
        StringBuilder content = new StringBuilder();
        List<JsonObject> resultList = wus.returnResult(encoded);
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
        //指定作用 若判断出了当前学期成绩不为空则填充本学期成绩说明，之后标识为false，不再填充说明以及本学期尚未公布成绩说明
        String[] strings = fillData(resultList, now, true);
        content.append(strings[0]);
        boolean flag = Boolean.valueOf(strings[1]);
        if (flag) {
            String lastSemester = TimeUtil.lastSemester();
            String last = String.valueOf(Integer.parseInt(nowYear) - 1) +
                    "-" +
                    TimeUtil.nowYear() +
                    "-" +
                    lastSemester;
            content.append("本学期【");
            content.append(now);
            content.append("】成绩尚未公布哦~\n----------------\n");
            content.append("上学期成绩如下:\n");
            if (!("未查询到数据".equals(resultList.get(0).get("成绩").getAsString()))) {
                strings = fillData(resultList, last, false);
                content.append(strings[0]);
            } else {
                content.append("未查询到考试成绩~");
            }

        }
        content.append("\n----------------\n");
        /*
         content.append("<a href=\"http://www.melloyuki.cn/api/jwxt/results?id=");
         content.append(id);
         content.append("\">点击查询全部成绩</a>");
         没有人写填充成绩的前端界面 因此采取折中方法
         */

        return content.toString();
    }

    /**
     * 返回组装好的课程表字符串
     *
     * @param wus     用户服务类 获取考试时间json数据列表
     * @param encoded 用户学号与密码的Base64加密组合
     * @return 返回组装好的字符串
     * @throws IOException IO流异常
     */
    private String getCourse(WxUserService wus, String encoded) throws IOException {
        StringBuilder content = new StringBuilder();
        List<JsonObject> courseList = wus.returnCourse(encoded);
        courseList.remove(49);//移除社会实践
        content.append("今天是第");
        int weeks = TimeUtil.getWeeks(); //周数
        content.append(weeks);
        content.append("周星期");
        String day = TimeUtil.now().getDayOfWeek().toString(); //星期
        content.append(ArabicToChineseUtil.arabicToChina(TimeUtil.getDayOfWeek(day)));
        content.append(".\n");
        content.append("你的课表如下:\n----------------\n");
        boolean flag = true;
        String name="";
        for (JsonObject jsonObject : courseList) {
            if (TimeUtil.getDayOfWeek(day).equals(jsonObject.get("weekday").getAsString())) {
                String s = jsonObject.get("class").getAsString();
                if (!"".equals(s)) {
                    JsonObject json = new JsonParser().parse(s).getAsJsonObject();
                    if(!name.equals(json.get("name").getAsString())){
                        content.append("课程:");
                        content.append(json.get("name").getAsString());
                        content.append("\n周次:");
                        content.append(json.get("weeks").getAsString());
                        content.append("\n地点:");
                        content.append(json.get("place").getAsString());
                        content.append("\n节数:");
                        content.append(json.get("section").getAsString());
                        content.append("\n------\n");
                    }
                    name=json.get("name").getAsString();
                    flag = false;
                }
            }
        }
        content.append("----------------\n");
        String data=content.toString();
        if (flag) {
            content.delete(0,data.length());
            content.append("今天是第");
            content.append(weeks);
            content.append("周星期");
            content.append(ArabicToChineseUtil.arabicToChina(TimeUtil.getDayOfWeek(day)));
            content.append(".\n");
            content.append("<(￣ˇ￣)/同学你没有课哦!");
        }
        return content.toString();
    }

    /**
     * 填充成绩数据
     *
     * @param resultList 全部的成绩数据
     * @param time       学期时间
     * @param semester   本学期还是上学期标识
     * @return 一个字符串数据 索引0是根据学期填充的数据  索引1 真或假 分别表示是否查询到本学期成绩 若是查询上一学期的可忽略该值
     */
    private String[] fillData(List<JsonObject> resultList, String time, boolean semester) {
        boolean flag = true;
        StringBuilder content = new StringBuilder();
        int index = 1;
        for (JsonObject jsonObject : resultList) {
            if (time.equals(jsonObject.get("开课学期").getAsString())) {
                if (flag) {
                    if (semester) {
                        content.append("本学期成绩【");
                        content.append(time);
                        content.append("】\n序号-科目-成绩-学分\n----------------\n");
                        flag = false;
                    } else {
                        content.append("序号-科目-成绩-学分\n");
                        flag = false;
                    }

                }
                content.append(index);
                content.append("-");
                content.append(jsonObject.get("课程名称").getAsString());
                content.append("-");
                content.append(jsonObject.get("成绩").getAsString());
                content.append("-");
                content.append(jsonObject.get("学分").getAsString());
                content.append("\n");
                index++;
            }
        }
        return new String[]{content.toString(), String.valueOf(flag)};
    }

}
