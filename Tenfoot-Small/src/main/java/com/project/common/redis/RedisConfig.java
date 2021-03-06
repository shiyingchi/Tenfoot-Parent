//package com.project.common.redis;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//@EnableCaching//开启注解
//public class RedisConfig extends CachingConfigurerSupport {
//	Logger logger = LoggerFactory.getLogger(RedisConfig.class);
//   @Bean
//    public CacheManager cacheManager(RedisTemplate<?,?> redisTemplate) {
//       CacheManager cacheManager = new RedisCacheManager(redisTemplate);
//       return cacheManager;
//    }
//
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
//       RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
//       redisTemplate.setConnectionFactory(factory);
//       return redisTemplate;
//    }
//}
