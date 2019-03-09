package com.everday.module_home.app;

import android.app.Application;

import com.everday.lib_base.inter.IAppComponet;

public class HomeApplcation implements IAppComponet {
    private static Application homeApplication;

    public static Application getInstanceHomeApplication(){return homeApplication;}

    @Override
    public void initAppliction(Application application) {
        homeApplication = application;
    }

    @Override
    public void initializationFailed(Object obj) {
        if(obj instanceof IllegalAccessException){
            ((IllegalAccessException) obj).getMessage();

        }
    }
}
