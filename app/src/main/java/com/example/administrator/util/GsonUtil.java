package com.example.administrator.util;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/1/27.
 */

public class GsonUtil {
    static Gson gson = new Gson();
    //将Json数据解析成相应的映射对象
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        T result = gson.fromJson(jsonData, type);
        return result;
    }
    public static String objectToJson(Object object){
        return gson.toJson(object);
    }
}
