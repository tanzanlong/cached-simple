package com.tzl.redis;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**http://blog.csdn.net/u010558660/article/details/51422365
 * @author Administrator
 *
 */
@Slf4j
public class JedisClientSimple {
	
	
	public void setKey(String key,String value){
		String host="196.168.212.24";
		int port=6379;
		/**
		 * 客户端连接超时 
		 */
		int connectionTimeout=1000;
		/**
		 * 客户端读写超时
		 */
		int soTimeout=1000;
		Jedis client=null;
		try{
			client=new Jedis(host, port, connectionTimeout, soTimeout);
			client.set(key, value);
		}catch(Exception e){
			log.error(" JedisClientSimple  setKey Exception:{}",e);
		}finally{
			if(client!=null){
				client.close();
			}
		}
	
	}
	
	
	public void dataSimple(){
		String host="196.168.212.24";
		int port=6379;
		/**
		 * 客户端连接超时 
		 */
		int connectionTimeout=1000;
		/**
		 * 客户端读写超时
		 */
		int soTimeout=1000;
		Jedis	jedis=new Jedis(host, port, connectionTimeout, soTimeout);
		// 1.string
		// 输出结果： OK
		jedis.set("hello", "world");
		// 输出结果： world
		jedis.get("hello");
		// 输出结果： 1
		jedis.incr("counter");
		// 2.hash
		jedis.hset("myhash", "f1", "v1");
		jedis.hset("myhash", "f2", "v2");
		// 输出结果： {f1=v1, f2=v2}
		jedis.hgetAll("myhash");
		// 3.list
		jedis.rpush("mylist", "1");
		jedis.rpush("mylist", "2");
		jedis.rpush("mylist", "3");
		// 输出结果： [1, 2, 3]
		jedis.lrange("mylist", 0, -1);
		// 4.set
		jedis.sadd("myset", "a");
		jedis.sadd("myset", "b");
		jedis.sadd("myset", "a");
		// 输出结果： [b, a]
		jedis.smembers("myset");
		// 5.zset
		jedis.zadd("myzset", 99, "tom");
		jedis.zadd("myzset", 66, "peter");
		jedis.zadd("myzset", 33, "james");
		// 输出结果： [[["james"],33.0], [["peter"],66.0], [["tom"],99.0]]
		jedis.zrangeWithScores("myzset", 0, -1);
		
		if(jedis!=null){
			jedis.close();
		}
	}

	
	
	public void  poolSimple(){
		// common-pool连接池配置， 这里使用默认配置， 后面小节会介绍具体配置说明
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		// 设置最大连接数为默认值的5倍
		poolConfig.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL * 5);
		// 设置最大空闲连接数为默认值的3倍
		poolConfig.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE * 3);
		// 设置最小空闲连接数为默认值的2倍
		poolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE * 2);
		// 设置开启jmx功能
		poolConfig.setJmxEnabled(true);
		// 设置连接池没有连接后客户端的最大等待时间(单位为毫秒)
		poolConfig.setMaxWaitMillis(3000);
		// 初始化Jedis连接池
		@SuppressWarnings("resource")
		JedisPool jedisPool = new JedisPool(poolConfig, "192.168.212.24", 6379);
		
		Jedis jedis = null;
		try {
		// 1. 从连接池获取jedis对象
		jedis = jedisPool.getResource();
		// 2. 执行操作
		log.info(jedis.get("hello"));
		} catch (Exception e) {
		log.error(e.getMessage(),e);
		} finally {
		if (jedis != null) {
		// 如果使用JedisPool， close操作不是关闭连接， 代表归还连接池
		jedis.close();
		}
		}
		
	}
	
}
