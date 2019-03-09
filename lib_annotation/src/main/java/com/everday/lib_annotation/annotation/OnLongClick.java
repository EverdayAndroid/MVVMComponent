package com.everday.lib_annotation.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerMethod = "setOnLongClickListener",listenerType = View.OnLongClickListener.class,callBackListener = "onLongClick")
public @interface OnLongClick {
    int[] value() default -1;
}
