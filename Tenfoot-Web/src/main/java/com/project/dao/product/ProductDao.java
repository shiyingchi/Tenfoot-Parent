package com.project.dao.product;


import com.project.domain.product.ProductCategory;
import com.project.domain.product.ProductCategoryMiddle;
import com.project.domain.product.ProductDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ProductDao {

	ProductDO get(String id);

	List<ProductDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(ProductDO product);

	int update(ProductDO product);

	int remove(String id);

	int batchRemove(String[] ids);

	/**
	 * 保存商品与分类关联
	 * @param productCategory
	 * @return
	 */
	int batchSaveProductCategory(List<ProductCategory> productCategory);

	/**
	 * 获取商品与分类关联
	 * @param productCategory
	 * @return
	 */
	String[] getProductCategory(ProductCategory productCategory);

	/**
	 * 批量删除商品与分类关联
	 */
	int batchRemoveProductCategory(Map<String, Object> map);

	/**
	 * 获取店铺商品与分类
	 * @return
	 */
	List<ProductCategoryMiddle> findShopProduct(Long userId);

	/**
	 * 获取所有分类关联
	 */
	List<ProductCategory> findProductCategoryList(Long userId);

	/**
	 * 保存分类关联
	 */
	int categorySave(ProductCategory productCategory);

}
