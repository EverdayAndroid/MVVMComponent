package com.everday.module_login.activity;

import android.arch.lifecycle.Observer;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.TextView;

import com.everday.lib_base.base.BaseActivity;
import com.everday.module_login.BR;
import com.everday.module_login.R;
import com.everday.module_login.databinding.LoginActivityLoginBinding;
import com.everday.module_login.model.LoginViewModel;
import com.jakewharton.rxbinding2.widget.RxTextView;

/**
 * @author Everday
 * @emil wangtaohandsome@gmail.com
 * create at 2019/3/26
 * description: 登陆界面
 */
public class LoginActivity extends BaseActivity<LoginActivityLoginBinding, LoginViewModel> {
    @Override
    public int initVariableId() {
        return BR.loginViewModel;
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.login_activity_login;
    }


    @Override
    public void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initViewObservable(){
        //监听ViewModel中pSwitchObservable的变化
        baseModel.uc.pSwitchObservable.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(baseModel.uc.pSwitchObservable.get()){
                    //密码可见
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //密码不可见
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                binding.etPassword.setSelection(binding.etPassword.getText().length());

            }
        });

        baseModel.getMutableLiveData().observe(this, new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object o) {
                refreshLayout();
            }
        });
    }

}
