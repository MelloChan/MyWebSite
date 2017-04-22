package com.mello.entity.message.req;

/**
 * Created by Administrator on 2017/4/5.
 * 图片bean
 */
public class ImageMessage extends BaseMessage {
    // 图片链接
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    @Override
    public String toString() {
        return "ImageMessage{" +
                "PicUrl='" + PicUrl + '\'' +
                '}';
    }
}
