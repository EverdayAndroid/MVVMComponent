package com.everday.lib_annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
/**
 * @author Everday
 * @emil wangtaohandsome@gmail.com
 * create at 2019/3/6
 * description: 拦截本应该执行的回调方法onClick(),而去执行我们自定义的方法（AOP概念）
 */
public class ListenerInvocationHandler implements InvocationHandler {
    private Object target;
    private HashMap<String,Method> methodHashMap = new HashMap<>();
    public ListenerInvocationHandler(Object object){
        this.target = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(target!=null){
            String methodName = method.getName();
            method = methodHashMap.get(methodName);
            if(method!=null && methodName.equals("onClick")){
                return method.invoke(target,args);
            }else if(method!=null && methodName.equals("onLongClick")){
                return method.invoke(target,args);
            }
        }

        return null;
    }

    public void setMethodHashMap(String methodName,Method method){
        methodHashMap.put(methodName,method);
    }
}
