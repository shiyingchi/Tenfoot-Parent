//package com.project.server.common.redis.impl;
//
//import com.project.server.common.redis.RedisService;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class RedisServiceImpl implements RedisService {
//
//	@Resource
//	private RedisTemplate<String, Object> redisTemplate;
//	@Resource
//	private StringRedisTemplate stringRedisTemplate;
//
//	private String buildKey(String cacheName, String key) {
//		return cacheName + '$' + key;
//	}
//
//	@Override
//	public void set(String key, Object value) {
//		ValueOperations<String, Object> vo = redisTemplate.opsForValue();
//		vo.set(key, value);
//	}
//
//	@Override
//	public Object get(String key) {
//		ValueOperations<String, Object> vo = redisTemplate.opsForValue();
//		return vo.get(key);
//	}
//
//	@Override
//	public void setString(String key, String value) {
//		stringRedisTemplate.opsForValue().set(key, value);
//	}
//
//	@Override
//	public String getString(String key) {
//		return stringRedisTemplate.opsForValue().get(key);
//	}
//
//	@Override
//	public void deleteKey(String key) {
//		redisTemplate.delete(key);
//	}
//
//	@Override
//	public void deleteStringKey(String key) {
//		stringRedisTemplate.delete(key);
//	}
//
//	@Override
//	public void setTime(String key, Long time) {
//		redisTemplate.expire(key, time, TimeUnit.MINUTES);
//	}
//
//	@Override
//	public void setStringTime(String key, Long time) {
//		stringRedisTemplate.expire(key, time, TimeUnit.MINUTES);
//	}
//
//	@Override
//	public Object get(String cacheName, String key) {
//		ValueOperations<String, Object> vo = redisTemplate.opsForValue();
//		return vo.get(buildKey(cacheName, key));
//	}
//
//	@Override
//	public void delete(String cacheName, String key) {
//		redisTemplate.delete(buildKey(cacheName, key));
//	}
//
//	@Override
//	public void put(String cacheName, String key, Object value, int time) {
//		ValueOperations<String, Object> vo = redisTemplate.opsForValue();
//		vo.set(buildKey(cacheName, key), value, time, TimeUnit.SECONDS);
//	}
//}