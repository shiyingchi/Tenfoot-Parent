package com.project.server.system.impl;

import com.project.dao.system.ParamDao;
import com.project.domain.system.ParamDO;
import com.project.server.system.ParamService;
import com.project.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;




@Service
public class ParamServiceImpl implements ParamService {
	@Autowired
	private ParamDao paramDao;

	@Override
	public List<ParamDO> list(){
		return paramDao.list();
	}


	/**
	 * 初始化参数
	 */
	@Override
	public void initParam() {
		List<ParamDO> list = list();
		for(ParamDO plist:list){
			Constant.PARAM_MAP.put(plist.getParamCode(),plist.getParamValue());
		}
	}

}
