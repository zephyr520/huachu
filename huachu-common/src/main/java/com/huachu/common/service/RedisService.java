package com.huachu.common.service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 *
 * @author Administrator
 * @date 2018/4/5
 */
@Component
public class RedisService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    public Collection<Object> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void delete(Collection<Object> key) {
        redisTemplate.delete(key);
    }

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, String value, long timeout) {
        set(key, value);
        expire(key, timeout);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void expire(String key, long seconds) {
        if(seconds > 0){
            stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        }
    }

    public void setContext(String context,String key,Object object){
        redisTemplate.opsForHash().put(context, key, object);
    }

    public Object getContext(String context,String key){
        if(redisTemplate.hasKey(context)) {
            return redisTemplate.opsForHash().get(context, key);
        } else {
            return null;
        }
    }
    public void deleteContext(String context,String key){
        if(redisTemplate.hasKey(context)) {
            redisTemplate.opsForHash().delete(context, key);
        }
    }
    public void deleteAllContext(String context){
        if(redisTemplate.hasKey(context))
            redisTemplate.opsForHash().delete(context);
    }

    public void increment(String key,Object value,int expired){
        redisTemplate.opsForValue().increment(key, (long)value);
        redisTemplate.expire(key, expired,TimeUnit.MINUTES);
    }

    public void incrementHours(String key,Object value,int expired){
        redisTemplate.opsForValue().increment(key, (long)value);
        redisTemplate.expire(key, expired,TimeUnit.HOURS);
    }

    public void setString(String key,Object value,int expired){
        redisTemplate.opsForValue().set(key, value, expired, TimeUnit.SECONDS);
    }

    public Object getString(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void removeString(String key){
        if(redisTemplate.hasKey(key))redisTemplate.delete(key);
    }

    public boolean setStringNX(String key,String value,int expired){
        boolean b = redisTemplate.opsForValue().setIfAbsent(key, value);
        if(b)redisTemplate.expire(key, expired, TimeUnit.MILLISECONDS);
        return b;
    }

    /**
     * 获取锁
     * @param key
     * @param expired 超时时间 毫秒
     * @return
     * @throws InterruptedException
     */
    public void getLock(String key,int expired) throws InterruptedException{
        boolean b = false;
        while (!b) {
            b = setStringNX(key,String.valueOf(System.currentTimeMillis()),expired);
            if(b) return;
            else {
                Thread.sleep(100);
            }
        }
    }

    /**
     * 删除锁
     * @param key
     */
    public void removeLock(String key){
        removeString(key);
    }

    public void set(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(value, SerializerFeature.UseSingleQuotes));
    }

    public <T> T get(String key, Class<T> clazz) {
        String value = stringRedisTemplate.opsForValue().get(key);
        return JSON.parseObject(value, clazz);
    }

    public void convertAndSend(String channel, String message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }
    
    public void setbit(String key,long userId) {
		stringRedisTemplate.opsForValue().setBit(key, userId, true);
	}
    
    /**
	 * 统计bit位为1的总数
	 * @param key
	 */
	public Long bitCount(final String key) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				long result = 0;
				result = connection.bitCount(key.getBytes());
				return result;
			}
		});
	}
}
