package com.tanzl.rocketmq.filter;

import java.io.IOException;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

public class FilterProducer {
    public static void main(String[] args) throws IOException, MQClientException, RemotingException, MQBrokerException, InterruptedException {DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
    producer.start();

    String tag="";
    for (int i = 0; i< 100; i++) {
        Message msg = new Message("TopicTest",
                tag,
                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            // Set some properties.
            msg.putUserProperty("a", String.valueOf(i));

            SendResult sendResult = producer.send(msg);
    }
  
    producer.shutdown();
    }  
      
    
}
