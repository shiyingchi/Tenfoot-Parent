package com.project.server.user;

import com.project.domain.user.TuserDO;

import java.util.Map;

/**
 * 会员表
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-05 17:56:44
 */
public interface TuserService {
	

	/**
	 * 授权登陆
	 */
	Map<String,Object> weixinLong(String code);



	/**
	 * 保存/修改用户信息
	 * @return
	 */
	Map<String,Object> userInformation(TuserDO tuserDO);


}
