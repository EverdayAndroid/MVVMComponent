package com.everday.module_login.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.everday.lib_base.base.BaseActivity;
import com.everday.lib_base.router.RouterActivityPath;
import com.everday.module_login.R;
import com.everday.module_login.R2;
import com.everday.module_login.BR;
import com.everday.module_login.databinding.LoginActivityLoginBinding;
import com.everday.module_login.entity.LoginViewModel;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginActivityLoginBinding,LoginViewModel> {
    private  LoginViewModel model;
    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.login_activity_login;
    }

    @Override
    public LoginViewModel initModel() {
        model = new LoginViewModel();
        return model;
    }

    @Override
    public void initData() {
        super.initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                model.setUserName("李四2");
                model.setPassword("33333333333333333333333333333");
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @OnClick({R2.id.btn})
    void OnClick(View view){
        int id = view.getId();
        if(id == R.id.btn){
            ARouter.getInstance().build(RouterActivityPath.MODULE_HOME.MODULE_HOMEACTVITY)
                    .withInt("age", 18)
                    .withString("name", "张三")
                    .navigation();
        }
    }


}
