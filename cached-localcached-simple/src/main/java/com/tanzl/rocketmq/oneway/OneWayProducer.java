package com.tanzl.rocketmq.oneway;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

public class OneWayProducer {
    public static void main(String[] args) throws MQClientException, InterruptedException,
            MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("oneway_group");
        producer.setNamesrvAddr("192.168.212.17:9876");
        producer.start();
        for (int i = 0; i < 1000; i++) {
            try {
                /*
                 * Create a message instance, specifying topic, tag and message body.
                 */
                Message msg =
                        new Message("TopicTest10", "TagA",
                                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                        );
                producer.sendOneway(msg);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }
        /*
         * Shut down once the producer instance is not longer in use.
         */
        producer.shutdown();
    }
}
