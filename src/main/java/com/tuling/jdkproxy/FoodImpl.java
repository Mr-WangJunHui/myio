package com.tuling.jdkproxy;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class FoodImpl  implements Food {
    @Override
    public void sayHi() {
        System.out.println("Welcome to Login!");
    }

    public void sayBee(){
        System.out.println("SayBee");
    }


}
