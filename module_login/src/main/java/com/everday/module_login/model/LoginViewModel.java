package com.everday.module_login.model;


import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.database.Observable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;
import com.everday.lib_base.base.BaseViewModel;
import com.everday.lib_base.command.BindingAction;
import com.everday.lib_base.command.BindingCommand;
import com.everday.lib_base.command.BindingConsumer;
import com.everday.lib_base.router.RouterActivityPath;
import com.everday.module_login.BR;
import com.everday.module_login.R;
import com.everday.module_login.entity.LoginBean;
import com.everday.module_login.entity.LoginInfoBean;
import com.everday.module_login.presenter.LoginPresenter;

/**
 * @author Everday
 * @emil wangtaohandsome@gmail.com
 * create at 2019/3/22
 * description: 数据存储层loginViewModel
 */

public class LoginViewModel extends BaseViewModel {
    //用户名
    public final ObservableField<String> userName = new ObservableField<>();
    //密码
    public final ObservableField<String> password = new ObservableField<>();
    //mac标识
    public final ObservableField<String> mac = new ObservableField<>();

    public LoginInfoBean loginInfoBean;

    private MutableLiveData<Object> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Object> getMutableLiveData() {
        return mutableLiveData;
    }

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public class UIChangeObservable {
        //密码开关观察者
        public ObservableBoolean pSwitchObservable = new ObservableBoolean(false);
    }

    //清除用户名的点击事件，逻辑从View层转换到ViewModel层
    public BindingCommand clearUserNameOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            userName.set("");
        }
    });
    //密码明文与暗文
    public BindingCommand passwordShowSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.pSwitchObservable.set(!uc.pSwitchObservable.get());
        }
    });
    //登陆
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            login();
        }
    });

    public void login() {
        if (TextUtils.isEmpty(userName.get())) {
            Toast.makeText(getApplication(), getApplication().getString(R.string.login_isempty_username), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            Toast.makeText(getApplication(), getApplication().getString(R.string.login_isempty_password), Toast.LENGTH_SHORT).show();
            return;
        }

        LoginPresenter presenter = new LoginPresenter(this);
        presenter.login();

        mutableLiveData.setValue("123");
        ARouter.getInstance().build(RouterActivityPath.MODULE_HOME.MODULE_HOMEACTVITY)
                .withInt("age", 18)
                .withString("name", userName.get())
                .navigation();
    }

    public LoginInfoBean getLoginInfoBean() {
        return loginInfoBean;
    }

    public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
        this.loginInfoBean = loginInfoBean;
    }
}
