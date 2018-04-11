package com.tzl.kafka.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerDemo {
	public static void main(String[] args) throws Exception{
		KafkaConsumer<String, String> consumer = KafkaUtil.getConsumer();
		consumer.subscribe(Arrays.asList("topictest1"));
		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(1000);
			for(ConsumerRecord<String, String> record : records) {
				System.out.println("fetched from partition " + record.partition() + ", offset: " + record.offset() + ", message: " + record.value());
			}
		}
	}
	
	
	

}
