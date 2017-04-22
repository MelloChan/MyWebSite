package com.mello.util;

import java.util.UUID;

/**
 * Created by Administrator on 2017/3/24.
 * 生成唯一的uuid
 */
public class ActiveCode {
    private ActiveCode(){}
    public static String getCode() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
