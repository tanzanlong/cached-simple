package com.tanzl.rocketmq.ordermsg;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest {
 public static void main(String[] args) throws IOException {
  
    
    new Thread(new Runnable() {
        public void run() {
            ServerSocket soServerSocket;
            try {
                soServerSocket = new ServerSocket(8082);
                soServerSocket.accept();
                System.out.println("111111");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
            
        }
    }).start();
    
    
    new Thread(new Runnable() {
        public void run() {
            ServerSocket soServerSocket;
            try {
                soServerSocket = new ServerSocket(8086);
                soServerSocket.accept();
                System.out.println("22222");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
            
        }
    }).start();
    
    
    new Thread(new Runnable() {
        public void run() {
            try {
                Socket  socket = new Socket("127.0.0.1", 8082);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
            
        }
    }).start();;
    
    new Thread(new Runnable() {
        public void run() {
            try {
                Socket  socket = new Socket("127.0.0.1", 8086);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
            
        }
    }).start();;
    
    
}
}
