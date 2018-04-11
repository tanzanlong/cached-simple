package com.tanzl.rocketmq.ordermsg;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

public class OrderProducer {
    public static void main(String[] args) throws IOException {  
        try {  
            DefaultMQProducer producer = new DefaultMQProducer("settlement-test-order");  
   
            producer.setNamesrvAddr("192.168.212.60:9876");  
   
            producer.start();  
   
            String[] tags = new String[] { "TagA", "TagC", "TagD" };  
              
            // 订单列表  
            List<OrderDemo> orderList =  new OrderProducer().buildOrders();  
              
            Date date = new Date();  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            String dateStr = sdf.format(date);  
            for (int i = 0; i < 10; i++) {  
                // 加个时间后缀  
                String body = dateStr + " Hello RocketMQ " + orderList.get(i);  
                Message msg = new Message("TopicTestOrder", tags[i % tags.length], "KEY" + i, body.getBytes());  
   
                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {  
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {  
                        Long id = (Long) arg;  
                        long index = id % mqs.size();  
                        return mqs.get((int)index);  
                    }  
                }, orderList.get(i).getOrderId());//订单id  
   
                System.out.println(sendResult + ", body:" + body);  
            }  
            /**
             * 根据订单取模，将同订单发送到同一队列。 队列不可用（队列与broker对应关系），策略如何？
             */
            producer.shutdown();  
  
        } catch (MQClientException e) {  
            e.printStackTrace();  
        } catch (RemotingException e) {  
            e.printStackTrace();  
        } catch (MQBrokerException e) {  
            e.printStackTrace();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        System.in.read();  
    }  
      
    /** 
     * 生成模拟订单数据  
     */  
    private List<OrderDemo> buildOrders() {  
        List<OrderDemo> orderList = new ArrayList<OrderDemo>();  
  
        OrderDemo orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103111039L);  
        orderDemo.setDesc("创建");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103111065L);  
        orderDemo.setDesc("创建");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103111039L);  
        orderDemo.setDesc("付款");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103117235L);  
        orderDemo.setDesc("创建");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103111065L);  
        orderDemo.setDesc("付款");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103117235L);  
        orderDemo.setDesc("付款");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103111065L);  
        orderDemo.setDesc("完成");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103111039L);  
        orderDemo.setDesc("推送");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103117235L);  
        orderDemo.setDesc("完成");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103111039L);  
        orderDemo.setDesc("完成");  
        orderList.add(orderDemo);  
          
        return orderList;  
    }  
    
}
