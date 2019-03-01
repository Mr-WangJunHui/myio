package com.tuling.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class LoginServiceProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("方法执行之前！");
       // method.invoke(o,objects);
       Object objReturn  = methodProxy.invokeSuper(o,objects);
        System.out.println("方法执行之后！");
        return null;
    }
}
