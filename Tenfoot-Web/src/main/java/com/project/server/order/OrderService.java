package com.project.server.order;


import com.project.domain.order.OrderDO;

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
public interface OrderService {
	
	OrderDO get(String id);
	
	List<OrderDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OrderDO order);
	
	int update(OrderDO order);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	/**
	 * 订单配送
	 */
	int orderDistribution(String id);

	/**
	 * 订单完成
	 */
	int orderComplete(String id);

	/**
	 * 退款
	 */
	Map<String,Object> orderRefund(String id, HttpServletRequest request);

	/**
	 * 每5分钟扫描订单 自动完成
	 */
	void updateOrder();
}
