package com.tzl.kafka.client.transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class ConsumerTest {
	@Test
	public void cousumer(){
		Properties props = new Properties();
      props.put("zookeeper.connect", "10.2.121.48:2181");
      props.put("zookeeper.session.timeout.ms", "40000");
      props.put("zookeeper.sync.time.ms", "200");
      props.put("auto.commit.interval.ms", "1000");
      props.put("group.id", "1");
      ConsumerConfig consumerConfig = new ConsumerConfig(props);
      ConsumerConnector consumer = Consumer.createJavaConsumerConnector(consumerConfig);
      
      Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
      topicCountMap.put("test", 1);
      Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
      KafkaStream<byte[], byte[]> stream = consumerMap.get("test").get(0);
      ConsumerIterator<byte[], byte[]> it = stream.iterator();
      while (it.hasNext()) {
          System.out.println("Receive->[" + new String(it.next().message()) + "]");
      }
	}
}
