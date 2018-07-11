package com.project.server.product;


import com.project.domain.product.CategoryDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2017-12-26 18:18:09
 */
public interface CategoryService {
	
	CategoryDO get(String id);

	//商品分页分类
	List<CategoryDO> list(Map<String, Object> map);

	//商品分类
	List<CategoryDO> categoryList(Long userIdCreate);
	
	int count(Map<String, Object> map);
	
	int save(CategoryDO category);
	
	int update(CategoryDO category);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
}
