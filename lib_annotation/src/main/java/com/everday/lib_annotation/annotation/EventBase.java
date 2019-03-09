package com.everday.lib_annotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    //监听方法
    String listenerMethod();
    //监听对象
    Class<?> listenerType();
    //回调方法
    String callBackListener();
}
