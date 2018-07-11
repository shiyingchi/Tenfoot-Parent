package com.project.server.system;


import com.project.domain.system.ParamDO;

import java.util.List;

/**
 * 参数表
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-05 16:13:19
 */
public interface ParamService {
	
	List<ParamDO> list();

	/**
	 * 初始化参数
	 */
	void initParam();
	
}
