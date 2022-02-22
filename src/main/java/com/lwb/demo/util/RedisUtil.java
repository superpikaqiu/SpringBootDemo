package com.lwb.demo.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;

/**
 * redis使用
 * @author lwb
 * @date 2020/9/17 9:11
 */
public class RedisUtil {
    //服务器IP地址
    private static String ADDR = "192.168.21.151";
    //端口
    private static int PORT = 6379;
    //密码
    private static String AUTH = "123";
    //连接实例的最大连接数
    private static int MAX_ACTIVE = 1024;
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
    private static int MAX_WAIT = 10000;
    //连接超时的时间　　
    private static int TIMEOUT = 10000;
    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;
    //数据库模式是16个数据库 0~15
    public static final int DEFAULT_DATABASE = 0;


    public static synchronized Jedis getJedis(){
        try{
            if(jedisPool == null){
                initJedisPool();
            }

            if(jedisPool != null){
                return jedisPool.getResource();
            } else{
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void initJedisPool(){
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT,null,DEFAULT_DATABASE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(final Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }

    public static String get(String key){
        Jedis jedis = null;
        String val = null;
        try{
            jedis = getJedis();
            val = jedis.get(key);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(jedis);
        }
        return val;
    }

    public static void set(String key, String val){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            jedis.set(key, val);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(jedis);
        }
    }

    public static String getMap(String key, String field){
        Jedis jedis = null;
        String val = null;
        try{
            jedis = getJedis();
            val = jedis.hget(key, field);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(jedis);
        }
        return val;
    }

    public static void setMap(String key, String field, String val){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            jedis.hset(key, field, val);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(jedis);
        }
    }

    public static Map<String, String> getMap(String key){
        Jedis jedis = null;
        Map<String, String> val = null;
        try{
            jedis = getJedis();
            val = jedis.hgetAll(key);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(jedis);
        }
        return val;
    }

    public static void setMap(String key, Map<String, String> val){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            jedis.del(key);
            jedis.hset(key, val);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(jedis);
        }
    }

    public static List<String> getList(String key){
        Jedis jedis = null;
        List<String> val = null;
        try{
            jedis = getJedis();
            val = jedis.lrange(key, 0, -1);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(jedis);
        }
        return val;
    }

    public static void setList(String key, List<String> val){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            jedis.del(key);
            jedis.lpush(key, val.toArray(new String[0]));
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(jedis);
        }
    }

    public static <T> void addList(String key, T val){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            jedis.lpush(key, val.toString());
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            close(jedis);
        }
    }

}
