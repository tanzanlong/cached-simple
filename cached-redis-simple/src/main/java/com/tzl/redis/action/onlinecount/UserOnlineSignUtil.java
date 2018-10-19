package com.tzl.redis.action.onlinecount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tzl.redis.util.JedisClient;
import com.tzl.util.DateUtil;
/**
 * 每当用户在某一天上线的时候,我们就使用SETBIT,以用户名作为key,
 * 将那天所代表的网站的上线日作为offset参数,并将这个offset上的为设置为1.
 * 比如,如果今天是网站上线的第100天,而用户$uid=10001在今天阅览过网站,
 * 那么执行命令SETBIT peter 100 1.
 * 如果明天$uid=10001也继续阅览网站,那么执行命令SETBIT peter 101 1 ,以此类推. 
 * 当要计算$uid=10001总共以来的上线次数时,就使用BITCOUNT命令: 
 * 执行BITCOUNT $uid=10001 ,得出的结果就是$uid=10001上线的总天数. 
 * 签到后如果需要奖励判断可以另存key(uid:reward:day),里面可以存储对应的奖励及领奖标记位. 
 */
@Component
public class UserOnlineSignUtil {
    
    private static final Long START_TIMESTRAMP = 1419091200L; // 首日签到时间 20141221
    
    private static final String USER_SIGN_KEY = "USER:SIGN:COUNT:";

    @Autowired
    private JedisClient jedisClient;
    
    /**获取签到key
     * @param uid
     * @return
     */
    public static String getSignKey(String uid){
        return USER_SIGN_KEY+uid;
    }
    
    
    
    /**签到记录
     * @param uId
     * @param day
     */
    public Boolean sign(String uId,Long timestampe){
        if (timestampe == null) {
            timestampe = System.currentTimeMillis();
        }
        String signKey=UserOnlineSignUtil.getSignKey(uId);
        long offset=(((timestampe/1000)-START_TIMESTRAMP) / 86400) + 1;
       return  jedisClient.setbit(signKey, offset, true);
    }
    
    
    public Long getSignCount(String uid) {
        String signKey=UserOnlineSignUtil.getSignKey(uid);
        return jedisClient.bitcount(signKey);
    }

    
    public Boolean getSign(String uid,Long timestampe) {
        if (timestampe == null) {
            timestampe = System.currentTimeMillis();
        }
        long offset=(((System.currentTimeMillis()/1000)-START_TIMESTRAMP) / 86400) + 1;
        String signKey=UserOnlineSignUtil.getSignKey(uid);
        return jedisClient.getbit(signKey, offset);
    }
    
    
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis()/1000);
        System.out.println(DateUtil.formatTradeTimeToGMT(System.currentTimeMillis()/1000,DateUtil.FORMAT_1)                          );
    }
}
