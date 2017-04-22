package com.mello.service;

import com.google.gson.JsonObject;
import com.mello.entity.WxUser;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/4/4.
 * 微信学生服务接口
 */
public interface WxUserService {
    //学生绑定接口
    boolean bindByNo(String encoded, WxUser wxUser);

    //是否重复绑定
    boolean hasWxUser(String sNumber);

    //解除绑定
    boolean removeByID(String openID);

    //返回学生成绩 JSON格式
    List<JsonObject> returnResult(String encoded) throws IOException;

    //返回学生考试时间 JSON格式
    List<JsonObject> returnExam(String encoded) throws IOException;

    //返回学生课表信息 JSON格式
    List<JsonObject> returnCourse(String encoded)throws IOException;
}
