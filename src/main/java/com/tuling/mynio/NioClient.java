package com.tuling.mynio;

public class NioClient {
    public static void main(String[] args) {
        Integer port = 8088;
        String  host = "127.0.0.1";
        if(args != null && args.length>0){
            port = Integer.valueOf(args[0]);
        }
        new Thread(new ClientHandle(port,host)).start();
    }
}
