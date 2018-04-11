package com.tanzl.rocketmq.producer;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.tanzl.rocketmq.constant.RocketmqConstant;
import com.tanzl.rocketmq.producer.config.RmqDefaultProducerConfig;
import com.tanzl.rocketmq.producer.factory.RmqProducerFactory;
@Slf4j
public class CommonProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException,
            RemotingException, MQBrokerException, InterruptedException {
        RmqProducerFactory rmqProducerFactory = new RmqProducerFactory();
        CommonProducer commonProducer = new CommonProducer();
        commonProducer.doInit(rmqProducerFactory);
        DefaultMQProducer producer =
                rmqProducerFactory.getProducer(RocketmqConstant.COMMON_MSG_GROUP);
        Message msg =
                commonProducer.buildMessage(RocketmqConstant.COMMON_MSG_TOPIC, "sub",
                        "orderId0001", "30");

        while (true) {
            producer.send(msg);
            log.info(" to send msg :{} ", msg.toString());
            Thread.sleep(3000);
        }


    }
  

  private void doInit(RmqProducerFactory rmqProducerFactory) throws MQClientException {
    log.info("Producer for cleaning settle  group start init");
    RmqDefaultProducerConfig cleaningSettleConfig =
        RmqDefaultProducerConfig.builder().retryTimesWhenSendAsyncFailed(1)
            .compressMsgBodyOverHowmuch(1024 * 4).retryAnotherBrokerWhenNotStoreOK(false)
            .maxMessageSize(1024 * 1024 * 4).defaultTopicQueueNums(4).sendMsgTimeout(3000)
            .retryTimesWhenSendFailed(2).topic(RocketmqConstant.COMMON_MSG_TOPIC)
            .producerGroup(RocketmqConstant.COMMON_MSG_GROUP).build();
    rmqProducerFactory.createDefaultProducer(cleaningSettleConfig);
    log.info("Producer for cleaning settle  group  init success!");

  }
  
  

  private Message buildMessage(String topic, String tags, String keys, String msgConent)
      throws UnsupportedEncodingException {
    Message msg =
        new Message(topic, tags, keys, msgConent.getBytes(RemotingHelper.DEFAULT_CHARSET));
    // msg.getProperties().put("traceID", String.valueOf(TraceContextLocal.getTraceId()));
    Long key=new Date().getTime();
    /**
     * 每个消息在业务层面的唯一标识码，要设置到 keys 字段，方便将来定位消息丢失问题
     */
    log.info(" key ;{}",key); 
    msg.setKeys(key.toString());
    return msg;
  }
  

  private void doSendMsg(DefaultMQProducer defaultMQProducer, Message msg,
          final Long eventID) {
    try {
      /**
         * 
         */
      defaultMQProducer.send(msg);
      /**
           * 
           */
      defaultMQProducer.send(msg, new SendCallback() {
        public void onSuccess(SendResult sendResult) {
          log.info("消息{},发送成功！", eventID);
          // 异步消息发送成功后，修改状体为已发送
          // eventMsgPersistenceService.updateEventStatusByID(eventID,
          // EventStatusConstant.HAVE_BEEN_SEND);
        }

        public void onException(Throwable e) {
          log.error("消息：{},发送异常，异常信息：{}", eventID, e.getMessage());
        }
      });
    } catch (Exception e) {
          log.error("消息：{},发送异常，异常信息：{}", eventID, e.getMessage());
      }
  }
}
