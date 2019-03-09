package com.everday.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.everday.lib_annotation.Everday;
import com.everday.lib_base.config.AppConfig;
import com.everday.lib_base.inter.IAppComponet;
import com.everday.lib_base.log.EverdayLog;
import com.everday.lib_base.utils.Utils;

/**
 * @author Everday
 * @emil wangtaohandsome@gmail.com
 * create at 2019/3/7
 * description: 专家个人信息界面
 */

public class EverdayApplication extends Application implements IAppComponet {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        if (Utils.isAppDebug()) {
            ARouter.openLog();
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
        }
        //初始化路由
        ARouter.init(this);
        //初始化Application
        initAppliction(this);
        //日志打印
        EverdayLog.initOpenLog(true);
    }

    @Override
    public void initAppliction(Application application) {
        for (String app : AppConfig.COMPONENTS) {
            try {
                Class<?> clazz = Class.forName(app);
                Object instance = clazz.newInstance();
                if(instance instanceof IAppComponet){
                    ((IAppComponet) instance).initAppliction(application);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                initializationFailed(e);
            } catch (InstantiationException e) {
                e.printStackTrace();
                initializationFailed(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                initializationFailed(e);
            }
        }
    }

    @Override
    public void initializationFailed(Object obj) {

    }
}
