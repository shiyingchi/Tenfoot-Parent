package com.project.controller.order;

import com.project.domain.order.OrderDO;
import com.project.server.order.OrderService;
import com.project.utils.PageUtils;
import com.project.utils.Query;
import com.project.utils.R;
import com.project.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 订单表
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-10 09:33:15
 */
 
@Controller
@RequestMapping("/order")
public class OrderController {
	private String prefix = "view/order/";

	@Autowired
	private OrderService orderService;
	
	@GetMapping()
	@RequiresPermissions("order:order")
	String Order(){
	    return prefix +"/order";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("order:order")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		query.put("sort","o.pay_create_time");
		query.put("order","desc");
		query.put("userId", ShiroUtils.getUserId());
		List<OrderDO> orderList = orderService.list(query);
		int total = orderService.count(query);
		PageUtils pageUtils = new PageUtils(orderList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("order:add")
	String add(){
	    return prefix + "/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("order:edit")
	String edit(@PathVariable("id") String id,Model model){
		OrderDO order = orderService.get(id);
		model.addAttribute("order", order);
	    return prefix + "/edit";
	}


	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("order:remove")
	public R remove( String id){
		if(orderService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("order:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		orderService.batchRemove(ids);
		return R.ok();
	}

	/**
	 * 订单配送
	 */
	@PostMapping( "/orderDistribution")
	@ResponseBody
	@RequiresPermissions("order:orderDistribution")
	public R orderDistribution(String id){
		if(orderService.orderDistribution(id)>0){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 订单完成
	 */
	@PostMapping( "/orderComplete")
	@ResponseBody
	@RequiresPermissions("order:orderComplete")
	public R orderComplete(String id){
		if(orderService.orderComplete(id)>0){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 退款
	 */
	@PostMapping( "/orderRefund")
	@ResponseBody
	@RequiresPermissions("order:orderRefund")
	public Map<String, Object> orderRefund(String id, HttpServletRequest request){
		return orderService.orderRefund(id,request);
	}


}
