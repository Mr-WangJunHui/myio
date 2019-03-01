package com.tuling.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyFood implements InvocationHandler {
    private Object object;
    public ProxyFood(Object obj){
        object = obj;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("方法执行之前！");
        method.invoke(object,args);
        System.out.println("方法执行之后！");
        return null;
    }
}
