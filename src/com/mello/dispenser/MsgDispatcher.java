package com.mello.dispenser;


import com.mello.service.WeChatService;
import com.mello.service.impl.WeChatServiceImpl;

import java.io.IOException;
import java.util.Map;

import static com.mello.util.MsgUtil.*;


/**
 * Created by Administrator on 2017/4/4.
 * 消息类调度器
 */
public class MsgDispatcher {
    public static String processMessage(Map<String,String>map) throws IOException {
        WeChatService wcs=new WeChatServiceImpl();
        String reqType=map.get("MsgType");
        String respInfo="";
        switch (reqType){
            case REQ_TYPE_TEXT:
                System.out.println("文本!");
                respInfo=wcs.textType(map);
                break;
            case REQ_TYPE_IMG:
                System.out.println("图片!");
                break;
            case REQ_TYPE_LINK:
                System.out.println("链接!");
                break;
            case REQ_TYPE_LOCATION:
                System.out.println("地理!");
                break;
            case REQ_TYPE_VOICE:
                System.out.println("音频!");
                break;
            case REQ_TYPE_VIDEO:
                System.out.println("视频!");
                break;
            case REQ_TYPE_SHORTVIDEO:
                System.out.println("小视频!");
                break;
            default:respInfo="Error!服务器出错!";
                break;
        }
        return respInfo;
    }
}
