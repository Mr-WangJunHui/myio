package com.tuling.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class CglibProxyTest {
    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\code1");
       // LoginServiceProxy loginServiceProxy = new LoginServiceProxy();
        //System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(LoginService.class);
        enhancer.setCallback(new LoginServiceProxy());
        LoginService loginService = (LoginService) enhancer.create();

        loginService.syHi();

    }
}
