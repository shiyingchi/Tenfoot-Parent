package com.project.utils;


import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public R() {
		put("code", 1);
		put("msg", "操作成功");
	}

	public static R error() {
		return error(0, "操作失败");
	}

	public static R error(String msg) {
		return error(500, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(int code,String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(int code,String msg,List<?> object) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		r.put("entity", object!=null?object:new JSONObject());
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public static Map<String,Object> hashMap(Map<String,Object> map){
		return map;

	}

	public static Map<String,Object> hashMapError(String message){
		Map<String,Object> map = new HashMap<>(16);
		map.put("code",0);
		map.put("entity",new JSONObject());
		map.put("msg",message);
		return map;
	}

	public static Map<String,Object> hashMapOk(String message,Object object){
		Map<String,Object> map = new HashMap<>(16);
		map.put("code",1);
		map.put("entity",object==null?new JSONObject():object);
		map.put("msg",message);
		return map;
	}

	public static Map<String,Object> hashMapOk(String message,List<?> array){
		Map<String,Object> map = new HashMap<>(16);
		map.put("code",1);
		map.put("entity",array);
		map.put("msg",message);
		return map;
	}
}
