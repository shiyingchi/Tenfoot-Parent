package com.project.server.system.impl;

import com.project.dao.system.UserDao;
import com.project.server.system.UserService;
import com.project.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 系统用户
 */

@Transactional
@Service
//@CacheConfig(cacheNames = "UserServiceImpl")
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserDao userMapper;



	@Override
//	@Cacheable(key = "#p0")
	public Map<String,Object> smallList(Double lat,Double lng) {
		if(lat == null || lng == null){
			return R.hashMapError("定位有误,信息查询失败");
		}else{
			try {
				return R.hashMapOk("查询成功", userMapper.smallList(lat,lng));
			}catch (Exception e){
				e.printStackTrace();
				return R.hashMapError("定位有误,信息查询失败");
			}

		}


	}

}
