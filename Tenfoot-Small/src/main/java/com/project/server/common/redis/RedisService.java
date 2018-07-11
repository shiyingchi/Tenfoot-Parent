package com.project.server.common.redis;

public interface RedisService {
	public void set(String key, Object value);

	public Object get(String key);

	public void setString(String key, String value);

	public String getString(String key);

	public void deleteKey(String key);

	public void deleteStringKey(String key);

	public void setTime(String key, Long time);

	public void setStringTime(String key, Long time);

	public Object get(String cacheName, String key);

	public void delete(String cacheName, String key);

	public void put(String cacheName, String key, Object value, int time);
}
