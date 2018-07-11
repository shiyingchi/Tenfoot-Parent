package com.project.dao.product;

import com.project.domain.product.OrderDO;
import com.project.domain.product.OrderDetailsDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 订单表
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-08 15:24:27
 */
@Mapper
@Repository
public interface OrderDao {

	OrderDO get0(String id);

	OrderDO get1(String id);

	List<OrderDetailsDO> getOrderProductList(String id);

	List<OrderDao> list(Map<String,Object> map);

	int save(OrderDO order);

	void update(OrderDO order);

	int batchSave(List<OrderDetailsDO> orderDetailsDO);
}
