package com.tzl.kafka.client;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

/**https://www.cnblogs.com/edison2012/p/5774207.html
 * 
 * http://kafka.apache.org/10/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html
 * 
 * http://orchome.com/303
 * @author Administrator
 *
 */
public class KafkaUtil {
	private static KafkaProducer<String, String> kp;
	private static KafkaConsumer<String, String> kc;
	
	public static KafkaProducer<String, String> getProducer() {
		if (kp == null) {
			Properties props = new Properties();
			props.put("bootstrap.servers", "192.168.212.24:9092");
			/**
			 * 请求完成的标准，配成all时，会造成阻塞，等待所有记录都提交后才返回。这种配置是最耗性能，也是最可靠的。
			 */
			//props.put("acks", "all");
			props.put("acks", "1");
			/**
			 * producer发送失败，会重试发送，如果配置成0，将不会重试发送。重试发送可能会导致消息重复。
			 */
			props.put("retries", 0);
			/**
			 * producer的缓冲区保存了各个分区未发送的数据，这个记录数可以通过batch.size配置，配置的越大，能保存的记录数越多，要求得内存也越大。比如我们 设置值为1时，程序
			 * 会等待1毫秒再行发送，这样同一批次就可以发送更多的记录。
			 */
			props.put("batch.size", 16384);
			
			/**
			 * 默认情况下，缓存区的记录是可以即时发送的，即使缓存区没有填满，但有时候我们想减少请求数，这时候我们可以通过linger.ms的设置来达到目的。
			 */
			props.put("linger.ms", 0);
			
			/**
			 * 控制生产者可用的缓存总量，如果消息发送速度比其传输到服务器的快，将会耗尽这个缓存空间。当缓存空间耗尽，其他发送调用将被阻塞，阻塞时间的阈值通过max.block.ms设定，之后它将抛出一个TimeoutException。
			 */
			props.put("buffer.memory", 0);
			
			
			props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			kp = new KafkaProducer<String, String>(props);
		}
		return kp;
	}
	
	
	
	public static KafkaConsumer<String, String> getConsumer() {
		if(kc == null) {
			Properties props = new Properties();
			props.put("bootstrap.servers", "192.168.212.24:9092");
			props.put("group.id", "1");
			props.put("enable.auto.commit", "true");
			props.put("auto.commit.interval.ms", "1000");
			props.put("session.timeout.ms", "30000");
			props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			kc = new KafkaConsumer<String, String>(props);
		}
		return kc;
	}
}
