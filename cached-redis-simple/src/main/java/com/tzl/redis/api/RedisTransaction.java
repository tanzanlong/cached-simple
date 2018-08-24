package com.tzl.redis.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;

import com.tzl.redis.util.JedisClusterManager;
@Component
public class RedisTransaction {


    private static Logger log = LoggerFactory.getLogger(RedisStringApi.class);

    private JedisCluster jedisCluster;
    
    @Autowired
    public void setJedisClusterManager(JedisClusterManager jedisClusterManager) {
        jedisCluster = jedisClusterManager.getJedisCluster();
    }

    
    
    /**设置新值并返回旧值
     * @param key
     * @param value
     * @return
     */
    public String getSet(String key,String value) {
        return jedisCluster.getSet(key, value);
    }
    

}
