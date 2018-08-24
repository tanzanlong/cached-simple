package com.tzl.util;

/**
 * Create By IntelliJ IDEA
 *
 */
public class SnowflakeIdUtil {

    private static SnowflakeIdWorker snowflakeIdWorker;

    public SnowflakeIdUtil(long workerId, long dataCenterId){
        snowflakeIdWorker = new SnowflakeIdWorker(workerId,dataCenterId);
    }

    public long nextId(){
        return snowflakeIdWorker.nextId();
    }
}
