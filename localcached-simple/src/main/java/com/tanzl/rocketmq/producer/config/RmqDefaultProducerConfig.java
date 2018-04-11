package com.tanzl.rocketmq.producer.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by tony.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RmqDefaultProducerConfig {
    /**
     * Just for testing or demo program, 该配置不能修改，不然会创建不了topic
     */
    //private String createTopicKey = MixAll.DEFAULT_TOPIC;
    /**
     * Producer group conceptually aggregates all producer instances of exactly same role, which is particularly
     * important when transactional messages are involved.
     * </p>
     *
     * For non-transactional messages, it does not matter as long as it's unique per process.
     * </p>
     *
     * See {@linktourl http://rocketmq.incubator.apache.org/docs/core-concept/} for more discussion.
     */
    private String producerGroup;
    /**
     * Number of queues to create per default topic.
     */
    private volatile int defaultTopicQueueNums = 4;

    /**
     * Timeout for sending messages.
     */
    private int sendMsgTimeout = 3000;

    /**
     * Compress message body threshold, namely, message body larger than 4k will be compressed on default.
     */
    private int compressMsgBodyOverHowmuch = 1024 * 4;

    /**
     * Maximum number of retry to perform internally before claiming sending failure in synchronous mode.
     * </p>
     *
     * This may potentially cause message duplication which is up to application developers to resolve.
     */
    private int retryTimesWhenSendFailed = 2;

    /**
     * Maximum number of retry to perform internally before claiming sending failure in asynchronous mode.
     * </p>
     *
     * This may potentially cause message duplication which is up to application developers to resolve.
     */
    private int retryTimesWhenSendAsyncFailed = 2;

    /**
     * Indicate whether to retry another broker on sending failure internally.
     */
    private boolean retryAnotherBrokerWhenNotStoreOK = false;

    /**
     * Maximum allowed message size in bytes.
     */
    private int maxMessageSize = 1024 * 1024 * 4; // 4M


    private String topic;
}
