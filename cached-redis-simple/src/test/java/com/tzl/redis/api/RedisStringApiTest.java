package com.tzl.redis.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tzl.redis.Bootstrap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
@WebAppConfiguration
public class RedisStringApiTest {
	@Autowired
	RedisStringApi redisStringApi;
	@Test
	public void testSet(){
		String result=redisStringApi.set("name", "tan");
		System.out.println(result);
	}
	
	@Test
	public void testGet(){
		String result=redisStringApi.get("name");
		System.out.println(result);
	}
	
	@Test
	public void testGetValueByRange(){
		String result=redisStringApi.getValueByRange("name");
		System.out.println(result);
	}
	
	@Test
	public void testGetSet(){
		String result=redisStringApi.getSet("name", "jiang");
		System.out.println(result);
	}
}
