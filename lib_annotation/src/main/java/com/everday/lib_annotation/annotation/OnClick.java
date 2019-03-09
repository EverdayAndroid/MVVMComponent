package com.everday.lib_annotation.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerMethod = "setOnClickListener",listenerType = View.OnClickListener.class,callBackListener = "onClick")
public @interface OnClick {
    int[] value() default -1;
}
