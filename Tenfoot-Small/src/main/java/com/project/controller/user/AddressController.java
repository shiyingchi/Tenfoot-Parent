package com.project.controller.user;

import com.project.domain.user.AddressDO;
import com.project.server.user.AddressService;
import com.project.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * 
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-06 19:14:52
 */
 
@Controller
@RequestMapping("/small/address")
public class AddressController {
	@Autowired
	private AddressService addressService;
	
	@ResponseBody
	@GetMapping("/list")
	public Map<String,Object> list(String openId,Long userId){

		return R.hashMapOk("查询成功", addressService.list(openId,userId));
	}

	@ResponseBody
	@GetMapping("/get")
	public Map<String,Object> get(String id){
		return addressService.get(id);
	}
	
	/**
	 * 保存/修改
	 */
	@ResponseBody
	@PostMapping("/saveOrUpdate")
	public Map<String,Object> saveOrUpdate(AddressDO address){
		return addressService.saveOrUpdate(address);
	}

	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	public Map<String,Object> remove( String id){
		return addressService.remove(id);
	}

	/**
	 * 获取默认地址
	 */
	@ResponseBody
	@PostMapping("/getDefaultAddress")
	public Map<String,Object> getDefaultAddress(String openId,Long userId){
		return addressService.getDefaultAddress(openId,userId);
	}
	
}
