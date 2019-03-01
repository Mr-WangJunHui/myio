package com.tuling.mynio;



import com.sun.xml.internal.ws.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ServerHandle implements Runnable{
    private Selector serSelector;
    private ServerSocketChannel serChannel;

    public ServerHandle(Integer port){
        try {
            this.serSelector = Selector.open();
            this.serChannel = ServerSocketChannel.open();
            this.serChannel.bind(new InetSocketAddress(port));
            this.serChannel.configureBlocking(false);
            this.serChannel.register(serSelector,SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        while (true){
            try {
                //从选择器中找到可用的通道
                SelectionKey key = null;
                int num = serSelector.select(1000);
                if (num > 0) {
                    Set<SelectionKey> seleKey = serSelector.selectedKeys();
                    Iterator<SelectionKey> iterator = seleKey.iterator();
                    while (iterator.hasNext()) {
                        key = iterator.next();
                        iterator.remove();
                        try {
                            handleReq(key);
                        } catch (Exception e) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        } finally {

                        }

                    }
                }


            } catch(IOException e){
                e.printStackTrace();
            }
        }


    }


    public void handleReq(SelectionKey key){
        //服务器端的操作
        if(key.isValid()){
            if(key.isAcceptable()){
                SocketChannel socketChannel =(SocketChannel)key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                //读取数据
                String  body = redaDateFromClient(socketChannel,byteBuffer);
                System.out.println("读到来自客户端的数据："+body);
                //在把数据写回去
                writerDataToClient(socketChannel,byteBuffer,body);

            }
            if(key.isReadable()){
                SocketChannel socketChannel =(SocketChannel)key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                //读取数据
                String  body = redaDateFromClient(socketChannel,byteBuffer);
                System.out.println("读到来自客户端的数据："+body);
                //在把数据写回去
                writerDataToClient(socketChannel,byteBuffer,body);

            }

        }
    }


    public void writerDataToClient(SocketChannel socketChannel,ByteBuffer byteBuffer,String body){
          //清空ByteBuffer内容
          byteBuffer.clear();
          if(body != null && body.length()>0){
              byte[] bytes = body.getBytes();
              byteBuffer.flip();
              byteBuffer.put(bytes);
              try {
                  socketChannel.write(byteBuffer);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }

    }


    public String redaDateFromClient(SocketChannel socketChannel,ByteBuffer byteBuffer){
        int num = 0;
        try {
            num = socketChannel.read(byteBuffer);
            if(num>0) {
                byteBuffer.flip();
                byte[] bytes = new byte[byteBuffer.remaining()];
                String body = new String(bytes, "UTF-8");
                return body;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       return null;
    }
}
