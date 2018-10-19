package com.tzl.redis.action.rank;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Tuple;

import com.tzl.redis.util.JedisClient;

@Component
public class RankRedisDAOImpl  { 
    @Autowired
    private JedisClient jedisClient;

    public Object get(String key) {
        Object o = jedisClient.get(key);
        return o;
    }

    public Object getSet(String name) {
        return null;
    }

    public Object getZSet(String name) {
        return null;
    }

    public void setValue(String key, String value) {
      jedisClient.set(key,value);
    }


    public void setSet(String name, User user) {

    }

    public void setZSet(String key,int id,double score) {
        this.addScore(key, id, score);
    }

    public void addScore(String key, int id, double score) {
      //  ZSetOperations zSet = redisTemplate.opsForZSet();
       // zSet.incrementScore(key,id,score);
        jedisClient.zincrby(key,score,String.valueOf(id));
    }

    
    public void updateScore(String key, int id, double score) {
       // ZSetOperations zSet = redisTemplate.opsForZSet();
       // zSet.add(key,id,score);
        jedisClient.zadd(key,score,String.valueOf(id));
    }

    
    public Set<String> getTop(String key,int top) {
        Set<String> set =jedisClient.zrevrange(key,0,top-1);
        return set;
    }

    
    public Set<Tuple> getTopWithScore(String key,int top) {
        Set<Tuple> zSet = jedisClient.zrevrangeWithScores(key,0,top-1);
        return zSet;
    }

    
    public Set<Tuple> getTopWithScore(String key, int start, int limit) {
        Set<Tuple> zSet = jedisClient.zrevrangeWithScores(key,start,start+limit-1);
        return zSet;
    }}
