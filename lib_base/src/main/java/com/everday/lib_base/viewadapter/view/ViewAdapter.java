package com.everday.lib_base.viewadapter.view;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.everday.lib_base.command.BindingCommand;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

public class ViewAdapter {
    //防重复点击间隔(秒)
    public static final int CLICK_INTERVAL = 1;

    /**
     *
     * @param view              View的onClick事件绑定
     * @param command           绑定的命令
     * @param isThrottleFirst   是否开启防止过快点击
     */
    @BindingAdapter(value = {"onClickCommand","isThrottleFirst"},requireAll = false)
    public static void onClickCommand1(View view, final BindingCommand command, boolean isThrottleFirst){
        if(isThrottleFirst){
            RxView.clicks(view)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            if(command!=null){
                                command.execute();
                            }
                        }
                    });
        }else{
            RxView.clicks(view)
                    .throttleFirst(CLICK_INTERVAL,TimeUnit.SECONDS) //1秒钟内允许点击一次
            .subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    if(command !=null){
                        command.execute();
                    }
                }
            });
        }
    }
}
