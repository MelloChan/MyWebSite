package com.mello.service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/5.
 * 处理各类型消息服务接口
 */
public interface WeChatService {
    //文本类型回复
    String textType(Map<String,String> map) throws IOException;
    //图片类型回复
    String imgType(Map<String,String>map);
    //链接内容回复
    String linkType(Map<String,String>map);
    //地理信息回复
    String locationType(Map<String,String>map);
    //音频类型回复
    String voiceType(Map<String,String>map);
    //视频类型回复
    String videoType(Map<String,String>map);
    //关注回复
    String subscribeType(Map<String,String>map);
    //菜单点击事件
    String clickType(Map<String,String>map) throws IOException;
    //二维码
    String scanType(Map<String,String>map);
}
