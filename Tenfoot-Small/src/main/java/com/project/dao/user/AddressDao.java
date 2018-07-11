package com.project.dao.user;


import com.project.domain.user.AddressDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-06 19:14:52
 */
@Mapper
@Repository
public interface AddressDao {

	AddressDO get(String id);
	
	List<AddressDO> list(Map<String,Object> map);
	
	int save(AddressDO address);
	
	int update(AddressDO address);
	
	int remove(String id);
	
	/**
	 * 获取用户默认地址
	 */
	AddressDO getDefaultAddress(@Param("openId") String openId, @Param("userId")Long userId);

	/**
	 * 修改为默认地址
	 */
	int removeDefault(AddressDO address);
}
