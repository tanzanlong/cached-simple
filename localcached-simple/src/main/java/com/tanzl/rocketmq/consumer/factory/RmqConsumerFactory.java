package com.tanzl.rocketmq.consumer.factory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.ServiceState;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.tanzl.rocketmq.constant.RocketmqConstant;
import com.tanzl.rocketmq.consumer.config.RmqDefaultConsumerConfig;

/**
 * Created by tony  tan 
 */
@Slf4j
@Service
public class RmqConsumerFactory {


  private final ConcurrentHashMap<String, DefaultMQPushConsumer> consumerMap =
      new ConcurrentHashMap<String, DefaultMQPushConsumer>();

  public DefaultMQPushConsumer getConsumerMap(String producerGroup) {
    DefaultMQPushConsumer defaultMQProducer = consumerMap.get(producerGroup);
    return defaultMQProducer;
  }

  public void createDefaultConsumer(RmqDefaultConsumerConfig rmqDefaultConsumerConfig)
      throws MQClientException {
    DefaultMQPushConsumer defaultMQPushConsumer =
        this.consumerMap.get(rmqDefaultConsumerConfig.getBloomConsumerGroup());
    if (defaultMQPushConsumer == null
        || defaultMQPushConsumer.getDefaultMQPushConsumerImpl().getServiceState() != ServiceState.RUNNING) {
      defaultMQPushConsumer =
          new DefaultMQPushConsumer(rmqDefaultConsumerConfig.getBloomConsumerGroup());
      this.consumerMap.put(rmqDefaultConsumerConfig.getBloomConsumerGroup(), defaultMQPushConsumer);
      defaultMQPushConsumer.setNamesrvAddr("192.168.212.17:9876");
      // 订阅指定MyTopic下tags等于MyTag
            defaultMQPushConsumer.subscribe(RocketmqConstant.COMMON_MSG_TOPIC,
          rmqDefaultConsumerConfig.getBloomsubExpression());

      // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
      // 如果非第一次启动，那么按照上次消费的位置继续消费
      defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
      // 设置为集群消费(区别于广播消费)
      defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
            defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                        ConsumeConcurrentlyContext context) {
                    System.out.printf(Thread.currentThread().getName() + " Receive New Messages: "
                            + msgs + "%n");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
      // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
      log.info("DefaultMQPushConsumer start success!");
      defaultMQPushConsumer.start();
      // defaultMQProducer.createTopic(rmqDefaultProducerConfig.getCreateTopicKey(),
      // rmqDefaultProducerConfig.getTopic(), 4);
    }
  }



}
