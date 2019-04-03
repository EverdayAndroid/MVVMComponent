package com.everday.lib_network.retrofit;

import com.everday.lib_base.utils.PreferencesUtils;
import com.everday.lib_network.OkHttpManager;
import com.everday.lib_network.constants.Constants;
import com.everday.lib_network.factory.ResponseConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitClient {
    private Retrofit retrofit;
    private static RetrofitClient retrofitClient;
    public static RetrofitClient getInstance() {
        if (retrofitClient == null) {
            synchronized (RetrofitClient.class) {
                if (retrofitClient == null) {
                    retrofitClient = new RetrofitClient();
                }
            }
        }
        return retrofitClient;
    }

    /**
     * 重置
     */
    public static void reset(){
        retrofitClient = null;
    }

    public RetrofitClient(){
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .client(OkHttpManager.getInstance().getOkClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ResponseConverterFactory.create(gson));
            String host  = (String) PreferencesUtils.get("network",Constants.HOST);
            builder.baseUrl(host);
            retrofit = builder.build();

        }
    }

    public Retrofit getRetrofit(){
        return  retrofit;
    }

    /**
     * 获取当前APP Host地址
     * @return
     */
    public static String getCurrentHost(){
        String host  = (String) PreferencesUtils.get("network",Constants.HOST);
        return host;
    }
}
