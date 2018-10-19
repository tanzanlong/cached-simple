package com.tzl.redis.action.rank;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Tuple;

public class RankServiceImpl {
    Logger  logger = LoggerFactory.getLogger(this.getClass());
    private static String RANK_LIST_NAME = "USER:SCORE:RANK";
    @Autowired
    private RankRedisDAOImpl rankRedisDAOImpl;

    public void addCore(int id, int score) {
        this.rankRedisDAOImpl.addScore(RANK_LIST_NAME,id, score);
    }

    public Set<String> getTop(int top) {
        return this.rankRedisDAOImpl.getTop(RANK_LIST_NAME,top);
    }
    public Set<Tuple> getTopWithScore(int top) {
        return this.rankRedisDAOImpl.getTopWithScore(RANK_LIST_NAME,top);
    }
    public Set<Tuple> getTopWithScore(int start,int limit) {
        return this.rankRedisDAOImpl.getTopWithScore(RANK_LIST_NAME,start,limit);
    }
}
