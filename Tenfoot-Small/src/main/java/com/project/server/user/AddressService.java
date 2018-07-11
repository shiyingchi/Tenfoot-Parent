package com.project.server.user;


import com.project.domain.user.AddressDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-06 19:14:52
 */
public interface AddressService {

	Map<String,Object> get(String id);
	
	List<AddressDO> list(String openId,Long userId);
	
	Map<String,Object> saveOrUpdate(AddressDO address);

	Map<String,Object> remove(String id);

	/**
	 * 获取默认地址
	 */
	Map<String,Object> getDefaultAddress(String openId,Long userId);
	
}
