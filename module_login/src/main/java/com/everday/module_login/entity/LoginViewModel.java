package com.everday.module_login.entity;


import android.databinding.Bindable;
import com.everday.module_login.BR;
import com.everday.lib_base.base.BaseModel;

public class LoginViewModel extends BaseModel {
    private String userName;
    private String password;

    public LoginViewModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginViewModel(){}

    @Bindable
    public String getUserName() {
        return userName == null ? "zhangsan" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }
    @Bindable
    public String getPassword() {
        return password == null ? "123456" : password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.userName);
    }
}
