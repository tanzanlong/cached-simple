package com.tanzl.rocketmq.producer.factory;

import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.ServiceState;
import com.tanzl.rocketmq.producer.config.RmqDefaultProducerConfig;

/**
 * Created by tony.
 */
@Slf4j
@Service
public class RmqProducerFactory {


    private final ConcurrentHashMap<String, DefaultMQProducer> producerMap =
            new ConcurrentHashMap<String, DefaultMQProducer>();

    public DefaultMQProducer getProducer(String producerGroup) {
        DefaultMQProducer defaultMQProducer = producerMap.get(producerGroup);
        return defaultMQProducer;
    }

    public void createDefaultProducer(RmqDefaultProducerConfig rmqDefaultProducerConfig) throws MQClientException {
        DefaultMQProducer defaultMQProducer = this.producerMap.get(rmqDefaultProducerConfig.getProducerGroup());
        if (defaultMQProducer == null || defaultMQProducer.getDefaultMQProducerImpl().getServiceState() != ServiceState.RUNNING) {
            defaultMQProducer = new DefaultMQProducer(rmqDefaultProducerConfig.getProducerGroup());
            this.producerMap.put(rmqDefaultProducerConfig.getProducerGroup(), defaultMQProducer);
            defaultMQProducer.setCompressMsgBodyOverHowmuch(rmqDefaultProducerConfig.getCompressMsgBodyOverHowmuch());
            defaultMQProducer.setDefaultTopicQueueNums(rmqDefaultProducerConfig.getDefaultTopicQueueNums());
            defaultMQProducer.setSendMsgTimeout(rmqDefaultProducerConfig.getSendMsgTimeout());
            defaultMQProducer.setRetryTimesWhenSendFailed(rmqDefaultProducerConfig.getRetryTimesWhenSendFailed());
            defaultMQProducer.setRetryTimesWhenSendAsyncFailed(rmqDefaultProducerConfig.getRetryTimesWhenSendAsyncFailed());
            defaultMQProducer.setMaxMessageSize(rmqDefaultProducerConfig.getMaxMessageSize());
            defaultMQProducer.setNamesrvAddr("192.168.212.17:9876");
            defaultMQProducer.start();
            //defaultMQProducer.createTopic(rmqDefaultProducerConfig.getCreateTopicKey(), rmqDefaultProducerConfig.getTopic(), 4);
        }
    }









}
