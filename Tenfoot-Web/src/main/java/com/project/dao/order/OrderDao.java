package com.project.dao.order;


import com.project.domain.order.OrderDO;
import com.project.domain.order.OrderDetialDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 订单表
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-10 09:33:15
 */
@Mapper
@Repository
public interface OrderDao {

	OrderDO get(String id);
	
	List<OrderDO> list(Map<String, Object> map);

	/**
	 * 查询所有已配送订单
	 * @return
	 */
	List<OrderDO> findOrderList();

	int updateOrder(@Param("ids") List ids);

	int count(Map<String, Object> map);
	
	int save(OrderDO order);
	
	int update(OrderDO order);
	
	int remove(String id);
	
	int batchRemove(String[] ids);



	List<OrderDetialDO> getOrderProductList(String id);

	/**
	 * 删除订单 也删除订单明细关联
	 */
	int removeDetail(String id);

	int batchRemoveDetail(String[] ids);
}
