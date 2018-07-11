package com.project.server.user.impl;

import com.project.dao.user.AddressDao;
import com.project.domain.user.AddressDO;
import com.project.server.user.AddressService;
import com.project.utils.R;
import com.project.utils.UUIDKey;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressDao addressDao;

	@Override
	public Map<String,Object> get(String id){
		if(StringUtils.isEmpty(id)){
			return R.hashMapError("查询失败,地址id不能为空");
		}else{
			AddressDO addressDO = addressDao.get(id);
			if(addressDO == null){
				return R.hashMapError("查询失败,无效地址id");
			}else{
				return R.hashMapOk("查询成功",addressDO);
			}
		}
	}
	
	@Override
	public List<AddressDO> list(String openId,Long userId){
		Map<String,Object> map = new HashMap<>(16);
		map.put("openId",openId);
		map.put("userId",userId);
		map.put("status","1");
		map.put("sort","create_time");
		map.put("order","desc");
		return addressDao.list(map);
	}
	
	@Transactional
	@Override
	public Map<String,Object> saveOrUpdate(AddressDO address){
		//删除默认地址
		addressDao.removeDefault(address);

		if(StringUtils.isEmpty(address.getId())){
			address.setId(UUIDKey.getUUID());
			address.setCreateTime(new Date());
			address.setStatus("1");
			address.setState("1");
			addressDao.save(address);
		}else{
			address.setState("1");
			addressDao.update(address);
		}


		return R.hashMapOk("操作成功",address);

	}
	
	@Override
	public Map<String,Object> remove(String id){
		if(StringUtils.isEmpty(id)){
			return R.hashMapError("查询失败,地址id不能为空");
		}else{
			AddressDO addressDO = addressDao.get(id);
			if(addressDO == null){
				return R.hashMapError("查询失败,无效地址id");
			}else{
				addressDO.setStatus("0");
				addressDao.update(addressDO);
				return R.hashMapOk("查询成功",new JSONObject());
			}
		}
	}

	/**
	 * 获取用户默认地址
	 */
	@Override
	public Map<String, Object> getDefaultAddress(String openId,Long userId) {
		if(StringUtils.isEmpty(openId)){
			return R.hashMapError("openId不能为空,获取默认地址失败");
		}else if(userId == null){
			return R.hashMapError("门店ID不能为空,获取默认地址失败");
		}else{
			AddressDO addressDO = addressDao.getDefaultAddress(openId,userId);
			if(addressDO == null){
				return R.hashMapError("查询失败,未设置默认地址");
			}else{
				return R.hashMapOk("查询成功",addressDO);
			}
		}
	}

}
