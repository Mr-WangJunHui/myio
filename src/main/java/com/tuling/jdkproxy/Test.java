package com.tuling.jdkproxy;

import net.sf.cglib.core.DebuggingClassWriter;

import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {

        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Food food = new FoodImpl();
        ProxyFood proxyFood = new ProxyFood(new FoodImpl());

        //被代理的类是FoodImpl,   Food是JDK必须基于接口实现


         //第一个参数：一个类加载器，任意一个类都可以，只有是个类加载器就可以了
         //第二个参数：被代理类的所有实现接口的列表
         //第三个参数：代理逻辑处理类的实例对象
        Food food1 = (Food) Proxy.newProxyInstance(Test.class.getClassLoader(),FoodImpl.class.getInterfaces(),proxyFood);
        food1.sayHi();
    }
}
