package com.mello.entity.message.resp;

/**
 * Created by Administrator on 2017/4/5.
 * 响应消息bean
 */
public class BaseMessage {
    //发送方openId
    private String ToUserName;
    //开发者微信号
    private String FromUserName;
    //发送时间
    private long CreateTime;
    //消息类型
    private String  MsgType;
//    //消息标记
    private int FuncFlag;


    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public int getFuncFlag() {
        return FuncFlag;
    }

    public void setFuncFlag(int funcFlag) {
        FuncFlag = funcFlag;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "ToUserName='" + ToUserName + '\'' +
                ", FromUserName='" + FromUserName + '\'' +
                ", CreateTime=" + CreateTime +
                ", MsgType='" + MsgType + '\'' +
                ", FuncFlag=" + FuncFlag +
                '}';
    }
}

