package com.everday.module_compress.app;

import android.app.Application;

import com.everday.lib_base.inter.IAppComponet;

public class ImageApplication implements IAppComponet {
    private static Application application;

    public static Application getInstanceHomeApplication(){return application;}
    @Override
    public void initAppliction(Application app) {
        application = app;
    }

    @Override
    public void initializationFailed(Object obj) {

    }
}
