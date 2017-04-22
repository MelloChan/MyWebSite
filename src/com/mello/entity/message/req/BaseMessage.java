package com.mello.entity.message.req;

/**
 * Created by Administrator on 2017/4/4.
 * 接收基本信息bean
 */
public class BaseMessage {
    //开发者微信号
    private String ToUserName;
    //发送方id
    private String FromUserName;
    //发送时间
    private long CreateTime;
    //消息类型
    private String  MsgType;
    //消息id
    private long MsgID;

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

    public long getMsgID() {
        return MsgID;
    }

    public void setMsgID(long msgID) {
        MsgID = msgID;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "ToUserName='" + ToUserName + '\'' +
                ", FromUserName='" + FromUserName + '\'' +
                ", CreateTime=" + CreateTime +
                ", MsgType='" + MsgType + '\'' +
                ", MsgID=" + MsgID +
                '}';
    }
}
