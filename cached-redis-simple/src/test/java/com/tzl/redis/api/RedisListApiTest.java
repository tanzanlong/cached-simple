package com.tzl.redis.api;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;
import com.tzl.redis.Bootstrap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
@WebAppConfiguration
public class RedisListApiTest {
    @Autowired
    RedisListApi redisListApi;
    @Test
    public void testSet(){
        Long result=redisListApi.lpush("listTest", "3");
        System.out.println(result);
    }

    @Test
    public void testRpush() {
        Long result = redisListApi.rpush("listTest", "2");
        System.out.println(result);
    }
    @Test
    public void testLrange() {
        List<String> result = redisListApi.lrange("listTest");
        System.out.println(JSON.toJSONString(result));
    }
    
    @Test
    public void testLrpop() {
        String result = redisListApi.rpop("listTest");
        System.out.println(JSON.toJSONString(result));
    }
}
