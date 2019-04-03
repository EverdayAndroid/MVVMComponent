package com.everday.module_login.inter;


import com.everday.module_login.entity.LoginInfoBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Everday
 * @emil wangtaohandsome@gmail.com
 * create at 2019/3/29
 * description: 登陆接口地址
 */
public interface LoginInterface {

    @POST("login")
    Observable<LoginInfoBean> login(@Body RequestBody requestBody);
}
