package com.everday.lib_network.factory;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/21
* description: gson解析类
*/

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private Type type;
    public GsonResponseBodyConverter(Gson gson, Type type){
        this.gson = gson;
        this.type = type;
    }
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        return gson.fromJson(response,type);
    }
}