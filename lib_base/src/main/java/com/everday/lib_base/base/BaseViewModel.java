package com.everday.lib_base.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.everday.lib_base.event.SingleLiveEvent;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;
import java.util.Map;

/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/8
* description: 数据model
*/

public class BaseViewModel extends AndroidViewModel implements IBaseViewMode{
    private LifecycleProvider lifecycleProvider;
    private UIChangeLiveData uc;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void registerRxBus() {

    }

    @Override
    public void removeRxBus() {

    }

    /**
     * 注入RxLifecycle生命周期
     * @param lifecycleProvider
     */
    public void injectLifecycleProvider(LifecycleProvider lifecycleProvider){
        this.lifecycleProvider = lifecycleProvider;
    }

    public LifecycleProvider getLifecycleProvider() {
        return lifecycleProvider;
    }

    public UIChangeLiveData getUC(){
        if(uc == null){
            uc = new UIChangeLiveData();
        }
        return uc;
    }

    public void showDialog(){
        showDialog("请稍后...");
    }

    public void showDialog(String title) {
        uc.showDialogEvent.setValue(title);
    }

    public void dismissDialog(){
        uc.dismissDialogEvent.call();
    }

    public void startActivity(Class<?> clzz){
        startActivity(clzz,null);
    }

    /**
     *  跳转页面
     * @param clzz   所跳转的目的Activity类
     * @param bundle  跳转携带信息
     */
    public void startActivity(Class<?> clzz, Bundle bundle){
        Map<String,Object> params = new HashMap<>();
        params.put(ParameterField.CLASS,clzz);
        if(bundle!=null){
            params.put(ParameterField.BUNDLE,bundle);
        }
        uc.startActivityEvent.postValue(params);
    }

    /**
     * 跳转容器页面
     * @param canonicalName
     */
    public void stattContainerActivity(String canonicalName){
        stattContainerActivity(canonicalName,null);
    }

    /**
     * 跳转容器页面
     * @param canonicalName  规范名：Activity.class.getCanonicalName();
     * @param bundle
     */
    public void stattContainerActivity(String canonicalName, Bundle bundle) {
        Map<String,Object> params = new HashMap<>();
        params.put(ParameterField.CANONICAL_NAME,canonicalName);
        if(bundle!=null){
            params.put(ParameterField.BUNDLE,bundle);
        }
        uc.startContainerActivityEvent.postValue(params);
    }
    //关闭界面
    public void finish(){
        uc.getFinishEvent().call();
    }
    //返回上一层
    public void onBackPressed(){
        uc.getOnBackPressedEvent().call();
    }


    public final class UIChangeLiveData extends SingleLiveEvent{
        private SingleLiveEvent<String> showDialogEvent;
        private SingleLiveEvent<Void> dismissDialogEvent;
        private SingleLiveEvent<Map<String,Object>> startActivityEvent;
        private SingleLiveEvent<Map<String,Object>> startContainerActivityEvent;
        private SingleLiveEvent<Void> finishEvent;
        private SingleLiveEvent<Void> onBackPressedEvent;

        public SingleLiveEvent<String> getShowDialogEvent() {
            return showDialogEvent;
        }

        public SingleLiveEvent<Void> getDismissDialogEvent() {
            return dismissDialogEvent;
        }

        public SingleLiveEvent<Map<String, Object>> getStartActivityEvent() {
            return startActivityEvent;
        }

        public SingleLiveEvent<Map<String, Object>> getStartContainerActivityEvent() {
            return startContainerActivityEvent;
        }

        public SingleLiveEvent<Void> getFinishEvent() {
            return finishEvent;
        }

        public SingleLiveEvent<Void> getOnBackPressedEvent() {
            return onBackPressedEvent;
        }
        private SingleLiveEvent createLiveData(SingleLiveEvent liveData){
            if(liveData == null){
                liveData = new SingleLiveEvent();
            }
            return liveData;
        }

        @Override
        public void observe(LifecycleOwner owner, Observer observer) {
            super.observe(owner, observer);
        }
    }

    public static final class ParameterField{
        public static String CLASS = "CLASS";
        public static String CANONICAL_NAME = "CANONICAL_NAME";
        public static String BUNDLE = "BUNDLE";
    }


}
