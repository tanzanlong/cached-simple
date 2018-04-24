package com.tzl.redis.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;

import com.tzl.redis.util.JedisClusterManager;
//http://www.redis.net.cn/tutorial/3508.html
@Component
public class RedisStringApi {

    private static Logger log = LoggerFactory.getLogger(RedisStringApi.class);

    private JedisCluster jedisCluster;
    
    @Autowired
    public void setJedisClusterManager(JedisClusterManager jedisClusterManager) {
        jedisCluster = jedisClusterManager.getJedisCluster();
    }

    
    public String set(String key,String value) {
        return jedisCluster.set(key, value);
    }
    
    public String get(String key) {
        return jedisCluster.get(key);
    }

    public String getValueByRange(String key) {
        return jedisCluster.getrange(key, 0, 1);
    }
    
    /**设置新值并返回旧值
     * @param key
     * @param value
     * @return
     */
    public String getSet(String key,String value) {
        return jedisCluster.getSet(key, value);
    }
    
    public Boolean getbit(String key,long  offset) {
        return jedisCluster.getbit(key, offset);
    }
    
    public List<String> mget(String... key) {
        return jedisCluster.mget(key);
    }
}
