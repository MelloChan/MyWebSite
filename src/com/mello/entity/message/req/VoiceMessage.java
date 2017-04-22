package com.mello.entity.message.req;

/**
 * Created by Administrator on 2017/4/5.
 * 语音bean
 */
public class VoiceMessage extends BaseMessage {
    // 媒体ID
    private String MediaId;
    // 语音格式
    private String Format;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    @Override
    public String toString() {
        return "VoiceMessage{" +
                "MediaId='" + MediaId + '\'' +
                ", Format='" + Format + '\'' +
                '}';
    }
}
