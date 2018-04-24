package com.tzl.redis.util;

import java.util.HashSet;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClusterManager {
    /*private static final int DEFAULT_TIMEOUT = 1000;
    private static final int DEFAULT_MAX_REDIRECTIONS = 20;
    private static final int MAX_TOTAL = 100;
    private static final int MAX_IDLE = 40;
    private static final int MIN_IDLE = 20;
    private static final boolean TEST_ON_BORROW = true;
    private static final boolean TEST_WHILE_IDLE = true;
    private static final int MAX_WAITE = 30000;*/
    private String servers;
    private int timeout = 1000;
    private int maxRedirections = 20;
    private JedisCluster jedisCluster;

    public JedisClusterManager() {
    }

    public void init() {
        HashSet<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        String[] jedisPoolConfig = this.servers.split("[,]");
        int len$ = jedisPoolConfig.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            String server = jedisPoolConfig[i$];
            String[] sa = server.split("[:]");
            if (sa.length == 2) {
                String host = sa[0];
                int port = Integer.parseInt(sa[1]);
                jedisClusterNodes.add(new HostAndPort(host, port));
            }
        }

        JedisPoolConfig var9 = new JedisPoolConfig();
        var9.setMaxTotal(100);
        var9.setMaxIdle(40);
        var9.setMinIdle(20);
        var9.setMaxWaitMillis(30000L);
        var9.setTestOnBorrow(true);
        var9.setTestWhileIdle(true);
        this.jedisCluster = new JedisCluster(jedisClusterNodes, this.timeout, this.maxRedirections, new GenericObjectPoolConfig());
    }

    public JedisCluster getJedisCluster() {
        if (null == this.jedisCluster) {
            this.init();
        }

        return this.jedisCluster;
    }

    public String getServers() {
        return this.servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxRedirections() {
        return this.maxRedirections;
    }

    public void setMaxRedirections(int maxRedirections) {
        this.maxRedirections = maxRedirections;
    }
    
}
