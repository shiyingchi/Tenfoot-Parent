package com.project.dao.system;

import com.project.domain.system.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 */
@Repository
@Mapper
public interface UserDao {


	/**
	 * 小程序定位门店列表
	 */
	List<UserDO> smallList(@Param("lat")Double lat, @Param("lng")Double lng);

	/**
	 * 获取用户信息
	 */
	UserDO get(Long userId);
}
