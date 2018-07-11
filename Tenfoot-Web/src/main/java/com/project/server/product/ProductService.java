package com.project.server.product;


import com.project.domain.product.ProductCategory;
import com.project.domain.product.ProductDO;

import java.util.List;
import java.util.Map;

public interface ProductService {
	
	ProductDO get(String id);
	
	List<ProductDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProductDO product);
	
	int update(ProductDO product);
	
	int remove(String id);
	
	int batchRemove(String[] ids);

	Map<String,Object> findShopProduct(Long userId);

	List<ProductCategory> findProductCategoryList(Long userId);


	/**
	 * 保存分类关联
	 */
	int categorySave(ProductCategory productCategory);
}
