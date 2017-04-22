package com.mello.entity.message.req;

/**
 * Created by Administrator on 2017/4/5.
 * 文本信息bean
 */
public class TextMessage extends BaseMessage {

    //消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "Content='" + Content + '\'' +
                '}';
    }
}
