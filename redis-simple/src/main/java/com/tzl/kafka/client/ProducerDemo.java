package com.tzl.kafka.client;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerDemo {
	public static void main(String[] args) throws Exception{
		/**
		 * 线程安全 并且在线程间公用一个 会比多实例更加好的性能。
		 * 用完的producer不手动关闭 会造成资源泄露。
		 * send方法是异步的
		 */
		Producer<String, String> producer = KafkaUtil.getProducer();
		int i = 0;
		while(true) {
			ProducerRecord<String, String> record = new ProducerRecord<String, String>("topictest1", String.valueOf(i), "this is message"+i);
			producer.send(record, new Callback() {
				public void onCompletion(RecordMetadata metadata, Exception e) {
					if (e != null)
						e.printStackTrace();
					System.out.println("message send to partition " + metadata.partition() + ", offset: " + metadata.offset());
				}
			});
			i++;
			Thread.sleep(1000);
		}
	}
}
