# redis-demo
kafka 

1.出现异常 org.apache.kafka.common.errors.TimeoutException: Expiring 3 record(s) for zlikun_topic-0: 30046 ms has passed since batch creation plus linger time？

 出现上面错误，是因为kafka server.properties没有配置host.name=10.8.122.26;默认是null；
 
 http://www.jasongj.com/kafka/high_throughput/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io