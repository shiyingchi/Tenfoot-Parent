package com.project.dao.product;


import com.project.domain.product.ProductCategoryMiddle;
import com.project.domain.product.ProductDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductDao {


	/**
	 * 获取店铺商品与分类
	 * @return
	 */
	List<ProductCategoryMiddle> findShopProduct(Long userId);

	/**
	 * 获取商品规格属性
	 */
	ProductCategoryMiddle findProductSpec(String productId);

	/**
	 * 获取店铺所有商品
	 */
	List<ProductDO> list(Long userId);

}
