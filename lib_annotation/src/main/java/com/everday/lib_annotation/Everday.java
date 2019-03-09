package com.everday.lib_annotation;

import android.app.Activity;
import android.view.View;

import com.everday.lib_annotation.annotation.EventBase;
import com.everday.lib_annotation.annotation.InjectView;
import com.everday.lib_annotation.annotation.OnClick;
import com.everday.lib_annotation.annotation.OnLongClick;

import java.lang.annotation.Annotation;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Everday
 * @emil wangtaohandsome@gmail.com
 * create at 2019/3/6
 * description: 注解实现类
 */
public class Everday {
    private Everday(){throw  new RuntimeException("Can't instance me");}

    private static Map<String,SoftReference<Activity>> map = new HashMap<>();
    /**
     * 绑定当前Activity
     */
    public static void bind(Activity activity){
        SoftReference<Activity> softReference = new SoftReference<>(activity);
        map.put(activity.getClass().getName(),softReference);
        injectOnClick(activity);
        onLong(activity);
        injectViews(activity);
    }

    /**
     * 实现查找view
     * @param activity
     */
    private static void injectViews(Activity activity){
        Class<? extends Activity> clzz = activity.getClass();
        Field[] fields = clzz.getDeclaredFields();
        for (Field field:fields) {
            InjectView bindView = field.getAnnotation(InjectView.class);
            if(bindView!=null){
                try {
                    Method method = clzz.getMethod("findViewById", int.class);
                    int value = bindView.value();
                    Object view = method.invoke(activity, value);
                    field.setAccessible(true);
                    field.set(activity,view);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 点击事件
     * @param activity
     */
    private static void injectOnClick(Activity activity){
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method:methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation:annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if(annotationType!=null && annotation instanceof OnClick){
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    String listenerMethod = eventBase.listenerMethod();
                    Class<?> listenerType = eventBase.listenerType();
                    String callBackListener = eventBase.callBackListener();
                    //拦截当前Activity
                    ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                    handler.setMethodHashMap(callBackListener,method);
                    //通过代理方式，操作这个对象，并且拦截onClick
                    Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);

                    try {
                        Method valueMethod = annotationType.getDeclaredMethod("value");
                        int[] value = (int[])valueMethod.invoke(annotation);
                        for (int id :value) {
                            View viewById = activity.findViewById(id);
                            Method declaredMethod = viewById.getClass().getMethod(listenerMethod, listenerType);
                            declaredMethod.invoke(viewById,proxy);
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    /**
     * 长按事件
     * @param activity
     */
    private static void onLong(Activity activity){
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method:methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation:annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if(annotationType!=null&& annotation instanceof OnLongClick){
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    if(eventBase!=null){
                        String listenerMethod = eventBase.listenerMethod();
                        Class<?> listenerType = eventBase.listenerType();
                        String callBackListener = eventBase.callBackListener();
                        ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                        handler.setMethodHashMap(callBackListener,method);
                        //动态代理
                        Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);
                        try {
                            //找到注解方法
                            Method annotationMethod = annotationType.getMethod("value");
                            int[] viewId = (int[]) annotationMethod.invoke(annotation);
                            for (int id:viewId) {
                                View view = activity.findViewById(id);
                                Method viewMethod = view.getClass().getMethod(listenerMethod, listenerType);
                                viewMethod.invoke(activity,proxy);
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    /**
     * 释放资源
     * @param activity
     */
    public static void unbind(Activity activity){
        if(activity instanceof Activity){
            map.remove(activity.getClass().getName());
        }
    }
}
