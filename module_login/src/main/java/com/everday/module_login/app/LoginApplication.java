package com.everday.module_login.app;

import android.app.Application;

import com.everday.lib_base.inter.IAppComponet;

public class LoginApplication implements IAppComponet {

    public static Application loginApplication;

    public static Application getInstanceApplication(){return loginApplication;}

    @Override
    public void initAppliction(Application application) {
        loginApplication = application;
    }

    @Override
    public void initializationFailed(Object obj) {

    }
}
