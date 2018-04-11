package com.tanzl.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.tanzl.rocketmq.constant.RocketmqConstant;
import com.tanzl.rocketmq.consumer.config.RmqDefaultConsumerConfig;
import com.tanzl.rocketmq.consumer.factory.RmqConsumerFactory;
@Slf4j
public class CommonConsumer {

    public static void main(String[] args) throws MQClientException {
        CommonConsumer conCommonConsumer = new CommonConsumer();
        RmqConsumerFactory rmqConsumerFactory = new RmqConsumerFactory();
        conCommonConsumer.doConsumerInit(rmqConsumerFactory);
        System.out.printf("Consumer Started.%n");
  }
  
    public void doConsumerInit(RmqConsumerFactory rmqConsumerFactory) throws MQClientException {
    log.info("Producer for cleaning settle  group start init");
    RmqDefaultConsumerConfig rmqDefaultConsumerConfig =
        RmqDefaultConsumerConfig.builder().retryTimesWhenSendAsyncFailed(1)
            .compressMsgBodyOverHowmuch(1024 * 4).retryAnotherBrokerWhenNotStoreOK(false)
            .maxMessageSize(1024 * 1024 * 4).defaultTopicQueueNums(4).sendMsgTimeout(3000)
            .retryTimesWhenSendFailed(2).bloomTopic(RocketmqConstant.COMMON_MSG_TOPIC)
            .bloomConsumerGroup(RocketmqConstant.COMMON_MSG_GROUP).build();
    rmqConsumerFactory.createDefaultConsumer(rmqDefaultConsumerConfig);;
    log.info("Producer for cleaning settle  group  init success!");
  }
  
}
