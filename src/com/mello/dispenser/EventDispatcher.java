package com.mello.dispenser;

import com.mello.service.WeChatService;
import com.mello.service.impl.WeChatServiceImpl;

import java.io.IOException;
import java.util.Map;

import static com.mello.util.MsgUtil.*;

/**
 * Created by Administrator on 2017/4/4.
 * 事件监听调度器
 */
public class EventDispatcher {
    public static String processEvent(Map<String,String> map) throws IOException {
        WeChatService wcs=new WeChatServiceImpl();
        String reqType=map.get("Event");
        String respInfo="";
        switch (reqType){
            case EVENT_TYPE_SUBSCRIBE:
                System.out.println("关注!");
                break;
            case EVENT_TYPE_UNSUBCRIBE:
                System.out.println("取消关注!");
                break;
            case EVENT_TYPE_CLICK:
                System.out.println("菜单点击!");
                respInfo=wcs.clickType(map);
                break;
            case EVENT_TYPE_VIEW:
                System.out.println("菜单跳转!");
                break;
            case EVENT_TYPE_SCAN:
                System.out.println("二维码!");
                break;
            case EVENT_TYPE_LOCATION:
                System.out.println("地理信息!");
                break;
            default:respInfo="Error!服务器出错!";
                break;
        }
        return respInfo;
    }
}
