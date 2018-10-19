package com.tzl.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.*;
import redis.clients.util.JedisClusterCRC16;

import java.util.*;

/**
 * User: tan
 * Date: 2016/10/7 0007
 * Time: 下午 1:59
 * Description:jedis 客户端工具类
 * 需要在spring 上下文中加上如下配置：
 *
 <bean id="jedisClusterManager" class="com.baibei.component.redis.JedisClusterManager" init-method="init">
    <property name="servers" value="${REDIS.CLUSTER.SERVERS}" />
    <property name="timeout" value="${REDIS.CLUSTER.TIMEOUT}" />
    <property name="maxRedirections" value="${REDIS.CLUSTER.MAX_REDIRECTIONS}" />
 </bean>
 <bean id="jedisClient" class="com.baibei.wine.product.utils.jedis.JedisClient">
    <property name="jedisClusterManager" ref="jedisClusterManager"/>
 </bean>


 <b>然后在需要用到redis 操作的地方加入 即可使用</b>
 @AutoWare
 private JedisClient jedisClient;

 *
 */

@Component
public class JedisClient {

    private static Logger log = LoggerFactory.getLogger(JedisClient.class);

    private JedisCluster jedisCluster;
    
    @Autowired
    public void setJedisClusterManager(JedisClusterManager jedisClusterManager) {
        jedisCluster = jedisClusterManager.getJedisCluster();
    }

    /**
     * 删除制定key值
     * @param key
     */
    public Long delete(String key) {
        return jedisCluster.del(key);
    }

    /**
     * 模糊删除
     * @param key
     * @return
     */
    public Long deleteLike(String key){
        long c = 0;
        Set<String> keys = keys(key);
        for(String k:keys) {
            c += jedisCluster.del(k);
        }
        return c;
       // return (Long) jedisCluster.eval("return redis.call('del', unpack(redis.call('keys',KEYS[1])))",1, key);
    }

    /**
     * 模糊匹配批量删除
     * @param pattern 模糊匹配
     * @return
     */
    public Long batchDelete(String pattern) {
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for(String k : clusterNodes.keySet()){
            JedisPool jp = clusterNodes.get(k);
            Jedis connection = jp.getResource();
            try {
                connection.del((String[])(connection.keys(pattern).toArray()));
            } catch(Exception e){
                log.error("Getting keys error: {}", e);
            } finally{
                connection.close();//用完一定要close这个链接！！！
            }
        }
        return 0L;
    }


    /**
     * 设置map中的某个字段的值
     * @param key
     */
    public void hset(String key,String field,String value) {
        jedisCluster.hset(key,field,value);
    }


    /**
     * 获取制定map的所有field字段的列表
     * @param key
     */
    public Set<String> hkeys(String key) {
        Set<String> keys = jedisCluster.hkeys(key);
       return keys;
    }

    /**
     * 查询查询制定key 的list的结果
     * @param key redis key
     * @param start 开始范围
     * @param end 结束范围
     * @return
     */
    public List<String> lrange(String key,int start,int end){
        return jedisCluster.lrange(key,start,end);
    }

    /**
     * 制定key 的Map 数据的所有数据
     * @param key
     * @return
     */
    public Map<String,String> hgetAll(String key){
        return jedisCluster.hgetAll(key);
    }


    /**
     * 获取列表的长度
     * @param key
     * @return
     */
    public Long llen(String key){
        return jedisCluster.llen(key);
    }


    /**
     * 获取列表的长度
     * @param key
     * @return
     */
    public List<String> getListAll(String key){
        return jedisCluster.lrange(key,0,-1);
    }


    /**
     * 递增map总的某个字段的值
     * @param key 制定map 的key
     * @param field map 中的field
     * @return
     */
    public Long hincrBy(String key,String field,long count){
        return jedisCluster.hincrBy(key,field,count);
    }


    /**
     * 获取map 制定field的值
     * @param key
     * @param fields
     * @return
     */
    public Map<String,String> getFieldMap(String key,String ... fields){
        List<String> result = jedisCluster.hmget(key,fields);
        if(result != null && result.size() > 0) {
            Map<String,String> resultMap = new HashMap<String,String>();
            for(int i=0;i<fields.length;i++) {
                String field = fields[i];
                resultMap.put(field,result.get(i));
            }
            return resultMap;
        }
        return null;
    }

    /**
     * 获取map 制定field的值
     * @param key
     * @param field
     * @return
     */
    public String getField(String key,String field){
        List<String> result = jedisCluster.hmget(key,field);
        if(result != null && result.size() > 0) {
            return result.get(0);
        }
       return null;
    }


    /**
     * 模糊查询获取所有集群中的key值
     * @param pattern
     * @return
     */
        public TreeSet<String> keys(String pattern){
            //log.debug("Start getting keys...");
            TreeSet<String> keys = new TreeSet<String>();
            Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
            for(String k : clusterNodes.keySet()){
                //log.debug("Getting keys from: {}", k);
                JedisPool jp = clusterNodes.get(k);
                Jedis connection = jp.getResource();
                try {
                    keys.addAll(connection.keys(pattern));
                } catch(Exception e){
                    //log.error("Getting keys error: {}", e);
                } finally{
                    //log.debug("Connection closed.");
                    connection.close();//用完一定要close这个链接！！！
                }
            }
            //log.debug("Keys gotten!");
            return keys;
        }

    public boolean push(String key,Object value){
        boolean returnVale = false;
        try{
            String opraText = jedisCluster.set(key, value.toString());
            if("OK".equals(opraText)){
                returnVale = true;
            }else{
                returnVale = false;
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            returnVale = false;
        }
        return returnVale;
    }



    public boolean pushList(String key ,List<?> list){
        boolean returnVale = false;
        long dataLength = 0l;
        try{
            if(null != list && list.size() > 0){
               // Transaction t = jedisCluster.get
                for (Object object : list) {
                    dataLength = jedisCluster.lpushx(key, object.toString());
                    if(dataLength <= 0l){
                        returnVale = false;
                        jedisCluster.del(key);

                        break;
                    }
                }
                if(returnVale){

                    returnVale = true;
                }

                //t.exec();
            }else{
                returnVale = false;
               // jedisCluster.lpushx(key, null);
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            returnVale = false;
        }

        return returnVale;
    }

    /**
     * 向缓存中设置对象
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,String value){
        boolean returnVale = false;
        try {
            String opraText = jedisCluster.set(key,value);
            if("OK".equals(opraText)){
                returnVale = true;
            }else{
                returnVale = false;
            }
            return returnVale;

        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return returnVale;
        }

    }

    /**
     * 向一个redis key中设置一个值，并返回旧的值
     * @param key
     * @param value
     * @return
     */
    public String getSet(String key,String value){
        String returnVal = jedisCluster.getSet(key,value);
        return "nil".equals(returnVal) ? null : returnVal;
    }

    /**
     * 向缓存中设置对象
     * @param key
     * @param seconds 过期时间
     * @param value
     * @return
     */
    public boolean setex(String key,int seconds , String value){
        boolean returnVale = false;
        try {
            String opraText = jedisCluster.setex(key,seconds ,value);
            if("OK".equals(opraText)){
                returnVale = true;
            }else{
                returnVale = false;
            }
            return returnVale;

        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return returnVale;
        }

    }


    /**
     * 通过key值查找redis集群中的redis对象
     * @param key
     * @return
     */
    public Jedis getJedisByKey(String key) {

        Map<String, JedisPool> nodeMap = jedisCluster.getClusterNodes();

        String anyHost = nodeMap.keySet().iterator().next();

        //getSlotHostMap方法在下面有

        TreeMap<Long, String> slotHostMap = getSlotHostMap(anyHost);

        //获取槽号

        int slot = JedisClusterCRC16.getSlot(key);

        //获取到对应的Jedis对象

        Map.Entry<Long, String> entry = slotHostMap.lowerEntry(Long.valueOf(slot));

        Jedis jedis = nodeMap.get(entry.getValue()).getResource();

        return jedis;
    }

    /**
     * 随机集群中的一台redis
     * @return
     */
    public Jedis getJedis() {
        Map<String, JedisPool> nodeMap = jedisCluster.getClusterNodes();
        return nodeMap.entrySet().iterator().next().getValue().getResource();
    }

    private static TreeMap<Long, String> getSlotHostMap(String anyHostAndPortStr) {
        TreeMap<Long, String> tree = new TreeMap<Long, String>();
        String parts[] = anyHostAndPortStr.split(":");
        HostAndPort anyHostAndPort = new HostAndPort(parts[0], Integer.parseInt(parts[1]));
        try{
            Jedis jedis = new Jedis(anyHostAndPort.getHost(), anyHostAndPort.getPort());
            List<Object> list = jedis.clusterSlots();
            for (Object object : list) {
                @SuppressWarnings("unchecked")
				List<Object> list1 = (List<Object>) object;
                @SuppressWarnings("unchecked")
				List<Object> master = (List<Object>) list1.get(2);
                String hostAndPort = new String((byte[]) master.get(0)) + ":" + master.get(1);
                tree.put((Long) list1.get(0), hostAndPort);
                tree.put((Long) list1.get(1), hostAndPort);
            }
            jedis.close();
        }catch(Exception e){

        }
        return tree;
    }

    public boolean hmset(String key, Map<String, String> data) {
        boolean returnVale = false;
        try{
            String opraText = jedisCluster.hmset(key,data);
            if("OK".equals(opraText)){
                returnVale = true;
            }else{
                returnVale = false;
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            returnVale = false;
        }
        return returnVale;
    }

    /**
     * 设置过期时间
     * @param key
     * @param sec
     * @return
     */
    public long expire(String key,int sec) {
        return jedisCluster.expire(key,sec);
    }

    /**
     * 设置过期时间
     * @param key
     * @param unixTime
     * @return
     */
    public long expireAt(String key,long unixTime) {
        return jedisCluster.expireAt(key,unixTime);
    }

    public boolean rpush(String key,String ... values) {
        boolean returnVale = false;
        try{
            Long len = jedisCluster.rpush(key,values);
            if(len == values.length){
                returnVale = true;
            }else{
                returnVale = false;
            }
        }catch(Exception e){
            log.error(e.getMessage(), e);
            returnVale = false;
        }
        return returnVale;
    }

    /**
     * 模糊查询Map
     * @param parent
     * @return
     */
    public List<Map<String,String>> getMapLike(String parent) {
        List<Map<String,String>> result = new ArrayList<Map<String,String>>();

        Set<String> keys = keys(parent);
        for(String key : keys) {
            Map<String,String> value = jedisCluster.hgetAll(key);
            if(value != null && value.size() > 0) {
                result.add(value);
            }
        }
        return result;
    }


    /**
     * 获取sort set 的所有member
     * @param key
     * @return
     */
    public List<String> getSortSetList(String key){
        return new ArrayList<String>(jedisCluster.zrange(key,0,-1));
    }

    /**
     * 获取sort set 的所有member
     * @param key
     * @return
     */
    public List<String> getSortSetList(String key,int start,int end){
        return new ArrayList<String>(jedisCluster.zrange(key,start,end));
    }

    /**
     * 获取sort set 的所有member
     * @param key
     * @return
     */
    public Set<Tuple> getSortSetWithScores(String key){
        return jedisCluster.zrangeWithScores(key,0,-1);
    }

    /**
     * 根据key值获取value
     * @param key
     * @return
     */

    /**
     * 将数据放入有序队列中 通过当前时间戳作为score当做排序
     * @param key
     * @param member
     * @return
     */
    public boolean zadd(String key,double score,String member){
        boolean returnVale = false;
        try{

            long len =jedisCluster.zadd(key,score,member);

            returnVale =  len == 1;
        }catch(Exception e){
            log.error(e.getMessage(), e);
            returnVale = false;
        }
        return returnVale;
    }

    public void eval(String script,int kecount,String ...params){
        jedisCluster.eval(script,kecount,params);
    }

    /**
     * 降序返回sortset 的结果集
     * @param key
     * @param start 开始下标
     * @param end 结束下标
     * @return
     */
    public Set<String> zrandDesc(String key,int start,int end){
        return jedisCluster.zrevrange(key,start,end);
    }

    /**
     * 升序返回sortset 的结果集
     * @param key
     * @param start 开始下标
     * @param end 结束下标
     * @return
     */
    public Set<String> zrandAsc(String key,int start,int end){
        return jedisCluster.zrevrange(key,start,end);
    }


    /**
     * 升序返回sortset 的结果集
     * @param key
     * @return
     */
    public String scriptLoad(String script,String key){
        return jedisCluster.scriptLoad(script,key);
    }

    public void evalsha(String sha,int keycount,String ...params){
        jedisCluster.evalsha(sha,keycount,params);
    }


    /**
     * 删除有序集合元素
     * @param key
     * @return
     */
    public Long zrem (String key,String ... members){
        return jedisCluster.zrem(key,members);
    }

    /**
     * 删除有序集合元素
     * @param key
     * @return
     */
    public Long zremrangeByRank (String key,long start,long end){
        return jedisCluster.zremrangeByRank(key,start,end);
    }

    /**
     * 删除有序集合中第一个元素
     * @param key
     * @return
     */
    public Long zremFirst (String key){
        return jedisCluster.zremrangeByRank(key,0,0);
    }


    /**
     * 删除有序集合中最后一个元素
     * @param key
     * @return
     */
    public Long zremLast (String key){
        return jedisCluster.zremrangeByRank(key,-1,-1);
    }

    /**
     * 获取有序集合中第一个元素
     * @param key
     * @return
     */
    public String zgetFirst (String key){
        Set<String> set = jedisCluster.zrange(key,0,0);
        if(set != null && set.size() > 0) {
            return set.toArray()[0].toString();
        }
        return null;
    }

    /**
     * 获取有序集合中最后一个元素
     * @param key
     * @return
     */
    public String zgetLast (String key){
        Set<String> set = jedisCluster.zrange(key,-1,-1);
        if(set != null && set.size() > 0) {
            return set.toArray()[0].toString();
        }
        return null;
    }



    public String get(String key) {
        return jedisCluster.get(key);
    }

    public Integer getInt(String key) {
        String value = jedisCluster.get(key);
        if(value != null && !value.equals("")) {
            return Integer.valueOf(value);
        }
        return 0;
    }

    /**
     * 将Key 的value递增count 返回递增后的值
     * @param key
     * @param count 递增增量
     * @return
     */
    public Long incrBy(String key,long count) {
        return jedisCluster.incrBy(key,count);
    }

    /**
     * 模糊匹配获取所有的SortSet
     * @param pattern 模糊匹配条件
     * @return
     */
    public Set<Tuple> getAllSortSetWithScore(String pattern) {
        Set<Tuple> results = new TreeSet<Tuple>();
        Set<String> keys = keys(pattern);
        for(String key : keys) {
            Set<Tuple> result = getSortSetWithScores(key);
            if(result != null && result.size() > 0) {
                results.addAll(result);
            }
        }
        return results;
    }


    /**
     * 模糊查询
     * @param parent
     * @return
     */
    public List<String> getLike(String parent) {
        List<String> result = new ArrayList<String>();

        Set<String> keys = keys(parent);
        for(String key : keys) {
            String value = jedisCluster.get(key);
            if(value != null) {
                result.add(value);
            }
        }
        return result;
    }

    /**
     * 模糊查询
     * @param parent
     * @return
     */
    public List<String> getListLike(String parent) {
        List<String> result = new ArrayList<String>();

        Set<String> keys = keys(parent);

        log.info("keys like parent="+parent + " result size="+keys.size());
        for(String key : keys) {
            try {
                List<String> row = jedisCluster.lrange(key,0,-1);
                if(row != null && row.size() > 0) {
                    result.addAll(row);
                }
            }catch (Exception e) {
                log.error("get list for key="+key+" error.",e);
            }
        }
        return result;
    }

    /**
     * 统计sortset 的元素数量
     * @param key
     * @return
     */
    public Long zcard(String key){
        return jedisCluster.zcard(key);
    }


    /**
     * 删除sortset 通过排序socre在start 与end之间的元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangeByScore(String key,long start,long end){
//        log.info("delete from redis key={},start={},end={}",key,start,end);
        return jedisCluster.zremrangeByScore(key,start,end);
    }

    /**
     * 发布一个消息
     * @param channel
     * @param message
     */
    public void publishMsg(String channel,String message){
        try {
            jedisCluster.publish(channel, message);
            log.debug("publishMsg {} = {}", channel, message);
        } catch (Exception e) {
//            log.warn("publishMsg {} = {}", channel, message, e);
        }
    }

    /**
     * 接收消息。在main方法调用后，会一直执行下去。当有发布对应消息时，就会在jedisPubSub中接收到！
     * @param jedisPubSub
     * @param channels
     */
    public void subscribeMsg(JedisPubSub jedisPubSub,String[] channels){
            jedisCluster.subscribe(jedisPubSub, channels);
            log.debug("subscribeMsg {} = {}", jedisPubSub, channels);
 
    }


    /**
     * 设置过期时间（毫秒）
     *
     * @param key
     * @param millis
     * @return
     */
    public long pexpire(String key, long millis) {
        return jedisCluster.pexpire(key, millis);
    }

    /**
     * 第一个为key，我们使用key来当锁，因为key是唯一的。
     * 
     * 第二个为value，我们传的是requestId，很多童鞋可能不明白，有key作为锁不就够了吗，为什么还要用到value？原因就是我们在上面讲到可靠性时，
     * 分布式锁要满足第四个条件解铃还须系铃人
     * ，通过给value赋值为requestId，我们就知道这把锁是哪个请求加的了，在解锁的时候就可以有依据。requestId可以使用UUID.randomUUID
     * ().toString()方法生成。
     * 
     * 第三个为nxxx，这个参数我们填的是NX，意思是SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
     * 
     * 第四个为expx，这个参数我们传的是PX，意思是我们要给这个key加一个过期的设置，具体时间由第五个参数决定。
     * 
     * 第五个为time，与第四个参数相呼应，代表key的过期时间。
     * 
     * @param key
     * @param value
     * @param millis
     * @return
     */
    public String setNxPx(String key, String value, long millis) {
        return jedisCluster.set(key, value, "NX", "PX", millis);
    }


    
    public long pttl(String key) {
        return jedisCluster.pttl(key);
    }

    public Long eval(String script, List<String> keys, List<String> args) {
        return (Long) jedisCluster.eval(script, keys, args);
    }
    
    public boolean setbit(String key,Long offset,boolean value) {
        return jedisCluster.setbit(key, offset, value);
    }

    public Long bitcount(String key) {
        return jedisCluster.bitcount(key);
    }
    
    public Boolean getbit(String key,Long offsets) {
        return jedisCluster.getbit(key, offsets);
    }
    
    
    public Set<Tuple> zrevrangeWithScores(String key, int start, int limit) {
     // 降序查询，并获取成员的分数
        Set<Tuple> tupleSet = jedisCluster.zrevrangeWithScores(key, start, limit);
        return tupleSet;
    }
    
    public Set<String> zrevrange(String key, int start, int end) {
        // 降序查询，并获取成员的分数
           Set<String> tupleSet = jedisCluster.zrevrange(key, start, end);
           return tupleSet;
       }
    
    public Double zincrby(String key, double score, String member) {
        // 降序查询，并获取成员的分数
           Double tupleSet = jedisCluster.zincrby(key, score, member);
           return tupleSet;
       }
}
