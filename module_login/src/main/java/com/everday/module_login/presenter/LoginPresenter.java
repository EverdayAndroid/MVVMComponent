package com.everday.module_login.presenter;

import com.everday.lib_base.presenter.BasePresenter;
import com.everday.lib_base.utils.PreferencesUtils;
import com.everday.lib_network.constants.Constants;
import com.everday.lib_network.retrofit.RetrofitClient;
import com.everday.module_login.entity.LoginInfoBean;
import com.everday.module_login.inter.LoginInterface;
import com.everday.module_login.model.LoginViewModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import retrofit2.Retrofit;

/**
 * @author Everday
 * @emil wangtaohandsome@gmail.com
 * create at 2019/3/29
 * description: 数据控制层
 */
public class LoginPresenter extends BasePresenter {
    private LoginInterface loginInterface;
    private LoginViewModel loginViewModel;
    private Retrofit retrofit;
    public LoginPresenter(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        retrofit = RetrofitClient.getInstance().getRetrofit();
        loginInterface = retrofit.create(LoginInterface.class);
    }

    /**
     * 登陆
     */
    public void login(){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("loginName", loginViewModel.userName.get());
        builder.add("password", loginViewModel.password.get());
        builder.add("mac", loginViewModel.mac.get());
        loginInterface.login(builder.build())

                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                    }
                })
                .subscribeOn(Schedulers.newThread())
                .doOnNext(new Consumer<LoginInfoBean>() {
                    @Override
                    public void accept(LoginInfoBean loginInfoBean) throws Exception {
                        if(loginInfoBean.getStatus() == Constants.SUCCESS){
                            if (loginInfoBean.getData().getSystem().equals(Constants.ROOMSYSTEM)){
                                roomSystem(loginInfoBean);
                            }else if(loginInfoBean.getData().getSystem().equals(Constants.POLICESYSTEM)){
                                policeSystem(loginInfoBean);
                            }
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginInfoBean>() {
                    @Override
                    public void accept(LoginInfoBean loginInfoBean) throws Exception {
                        if(loginInfoBean.getStatus() == Constants.SUCCESS){
                            loginViewModel.setLoginInfoBean(loginInfoBean);
                            loginViewModel.getMutableLiveData().setValue(loginViewModel);
                        }
                    }
                });
    }

    /**
     * 警务室系统
     * @param loginInfoBean
     */
    public void roomSystem(LoginInfoBean loginInfoBean){
        //保存token
        PreferencesUtils.put("token", loginInfoBean.getData().getToken(), false);
        PreferencesUtils.put("name", loginInfoBean.getData().getNickName(), false);
        PreferencesUtils.put("user_id", String.valueOf(loginInfoBean.getData().getUserId()), false);
        PreferencesUtils.put("telephone", loginInfoBean.getData().getTelephone(), false);
        PreferencesUtils.put("email", loginInfoBean.getData().getEmail(), false);
        PreferencesUtils.put("qq", loginInfoBean.getData().getQq(), false);
        PreferencesUtils.put("wechat", loginInfoBean.getData().getWechat(), false);
        PreferencesUtils.put("avatar", loginInfoBean.getData().getAvatar(), false);
        PreferencesUtils.put("signature", loginInfoBean.getData().getSignature(), false);
        PreferencesUtils.put("comments", loginInfoBean.getData().getComments(), false);
        PreferencesUtils.put("organizationName", loginInfoBean.getData().getOrganizationName(), false);

        PreferencesUtils.put("avatar", loginInfoBean.getData().getAvatar(), false);
        PreferencesUtils.put("roleCode", loginInfoBean.getData().getRoleCode(), false);
        PreferencesUtils.put("organizationId", loginInfoBean.getData().getOrganizationId(), false);

        PreferencesUtils.put("cityOrganizationId", loginInfoBean.getData().getCityOrganizationId(), false);
        PreferencesUtils.put("subOrganizationId", loginInfoBean.getData().getSubOrganizationId(), false);

        PreferencesUtils.put("cityOrganizationName", loginInfoBean.getData().getCityOrganizationName(), false);
        PreferencesUtils.put("subOrganizationName", loginInfoBean.getData().getSubOrganizationName(), false);

        PreferencesUtils.put("flag", loginInfoBean.getData().getFlag(), false);

        PreferencesUtils.put("system", loginInfoBean.getData().getSystem(), false);
    }

    /**
     * 派出所系统
     * @param loginInfoBean
     */
    public void policeSystem(LoginInfoBean loginInfoBean){
        //保存token
        PreferencesUtils.put("token", loginInfoBean.getData().getToken(), false);
        PreferencesUtils.put("loginName", "民警", false);
        PreferencesUtils.put("policeId", loginInfoBean.getData().getPoliceId(), false);
        PreferencesUtils.put("flag", loginInfoBean.getData().getFlag(), false);
        PreferencesUtils.put("identity", loginInfoBean.getData().getIdentity(), false);
        PreferencesUtils.put("policeName", loginInfoBean.getData().getPoliceName(), false);
        PreferencesUtils.put("name", loginInfoBean.getData().getAdminName(), false);
        PreferencesUtils.put("adminHeadImg", loginInfoBean.getData().getAdminHeadImg(), false);
        PreferencesUtils.put("user_id", String.valueOf(loginInfoBean.getData().getUserId()), false);
        PreferencesUtils.put("system", loginInfoBean.getData().getSystem(), false);
    }
}
