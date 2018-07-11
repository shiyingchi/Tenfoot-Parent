package com.project.server.system;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 系统用户
 */

@Service
public interface UserService {

	/**
	 * 小程序定位门店列表
	 */
	Map<String, Object> smallList(Double lat,Double lng);

}
