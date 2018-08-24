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
public class RedisListApi {

    private static Logger log = LoggerFactory.getLogger(RedisListApi.class);

    private JedisCluster jedisCluster;
    
    @Autowired
    public void setJedisClusterManager(JedisClusterManager jedisClusterManager) {
        jedisCluster = jedisClusterManager.getJedisCluster();
    }

    
    /**list 左边添加元素
     * @param key
     * @param value
     * @return
     */
    public Long lpush(String key,String value) {
        return jedisCluster.lpush(key, value);
    }
    
    /**list 右边添加元素
     * @param key
     * @param value
     * @return
     */
    public Long rpush(String key,String value) {
        return jedisCluster.rpush(key, value);
    }
    
    
    /**list 右边添加元素
     * @param key
     * @param value
     * @return
     */
    public List<String> lrange(String key) {
        return jedisCluster.lrange(key, 0, 3);
    }
    
    /**list 左边刪除元素并返回
     * @param key
     * @param value
     * @return
     */
    public String lpop(String key) {
        return jedisCluster.lpop(key);
    }
    
    /**list 右边刪除元素并返回
     * @param key
     * @param value
     * @return
     */
    public String rpop(String key) {
        return jedisCluster.rpop(key);
    }
}
