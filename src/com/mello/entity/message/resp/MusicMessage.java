package com.mello.entity.message.resp;

/**
 * Created by Administrator on 2017/4/5.
 * music bean
 */
public class MusicMessage extends BaseMessage {
    // 音乐
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }

    @Override
    public String toString() {
        return "MusicMessage{" +
                "Music=" + Music +
                '}';
    }
}
