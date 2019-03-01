package com.tuling.mynio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ClientHandle implements Runnable{
    private   Selector selector;
    private SocketChannel socketChannel;
    private String host;
    private Integer port;

    public ClientHandle(Integer port,String host){
        try {
            this.host = host;
            this.port = port;
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            //设置非阻塞
            socketChannel.configureBlocking(false);
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        //执行连接的操作
        try {
            socketChannel.connect(new InetSocketAddress(host,port));
           int num =  selector.select(1000);
           if(num>0){
               Set<SelectionKey> selectionKey = selector.selectedKeys();
               Iterator<SelectionKey> iterables = selectionKey.iterator();
               while (iterables.hasNext()){
                   SelectionKey key = iterables.next();
                   clientHandle(key);
                   if(key != null){
                      key.cancel();

                   }
                   if(key.channel() != null){
                       key.channel().close();
                   }
               }
           }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //客户端处理请求的操作
    public void clientHandle(SelectionKey key) throws IOException {
         if(key.isValid()){
             SocketChannel sc = (SocketChannel) key.channel();
             if(sc.finishConnect()){//判断是否已经连接上服务器
                 ByteBuffer byteBuffer = ByteBuffer.allocate(1000);
                 InputStreamReader in = new InputStreamReader(System.in);
                 BufferedReader bufferedReader = new BufferedReader(in);
                 if(bufferedReader == null){
                     throw new RuntimeException("输入数据不能位null");
                 }
                 String data;
                 if((data = bufferedReader.readLine())!=null){
                     System.out.println("你输入的数据为"+data);
                    //byteBuffer.flip();
                     byteBuffer.put(data.getBytes());
                     sc.write(byteBuffer);
                 }
             }

             if(key.isAcceptable()){
                 //可以接受数据

             }
             if(key.isConnectable()){
                 //可连接

             }
             if(key.isReadable()){
                 //可读

             }
             if(key.isWritable()){
                 //可写

             }

         }
    }
}
