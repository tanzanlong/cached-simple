package com.tzl.redis.design.update;

import org.springframework.beans.factory.annotation.Autowired;

import com.tzl.redis.util.JedisClient;

/**这是最常用最常用的pattern了。其具体逻辑如下：
      失效：应用程序先从cache取数据，没有得到，则从数据库中取数据，成功后，放到缓存中。
      命中：应用程序从cache中取数据，取到后返回。
      更新：先把数据存到数据库中，成功后，再让缓存失效。
 * @author tan
 *
 */
public class CacheAside {
    

    @Autowired
    private JedisClient jedisClient;
    
    public String query(String qKey){
        String qResult=jedisClient.get(qKey);
        if (qResult == null || "".equals(qResult.trim())) {
            String dbResult = this.queryFromDB(qKey);
            jedisClient.set(qKey, dbResult);
            return dbResult;
        }
        return qResult;
    }

    public void updateCache(String qKey,String value){
        this.updateDB(qKey, value);
    }
    
    
    public String queryFromDB(String qKey){
        return "";
    }    
    
     
    public String updateDB(String qKey,String value){
        return "";
    }
}
