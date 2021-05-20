package com.richer.myhospital.util;

import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class NetWorkUtil {

    public static void enqueueGet(String url, Map<String, String> headers, Map<String,String> params, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        if (headers != null) {
            Iterator<String> keys = headers.keySet().iterator();
            Iterator<String> values = headers.values().iterator();
            for (int i=0;i<headers.size();i++) {
                builder.addHeader(keys.next(), values.next());
            }
        }
        if (params != null) {
            url = addGetParams(url, params);
        }
        builder.url(url);
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static void enqueuePost(String url, Request request, RequestBody body, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static String addGetParams(String url, Map<String, String> paramsMap) {
        Iterator<String> keys = paramsMap.keySet().iterator();
        Iterator<String> values = paramsMap.values().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("?");
        for (int i=0;i<paramsMap.size();i++) {
            stringBuffer.append(keys.next()+"="+values.next());
            if (i != paramsMap.size()-1) {
                stringBuffer.append("&");
            }
        }
        return url + stringBuffer.toString();
    }

}
