package com.project.server.product.impl;

import com.project.dao.product.CategoryDao;
import com.project.domain.product.CategoryDO;
import com.project.server.product.CategoryService;
import com.project.utils.ShiroUtils;
import com.project.utils.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public CategoryDO get(String id){
		return categoryDao.get(id);
	}
	
	@Override
	public List<CategoryDO> list(Map<String, Object> map){
		return categoryDao.list(map);
	}

	@Override
	public List<CategoryDO> categoryList(Long userIdCreate) {
		return categoryDao.categoryList(userIdCreate);
	}

	@Override
	public int count(Map<String, Object> map){
		return categoryDao.count(map);
	}
	
	@Override
	public int save(CategoryDO category){
		category.setId(UUIDKey.getUUID());
		category.setGmtCreate(new Date());
		category.setUserIdCreate(ShiroUtils.getUserId());
		return categoryDao.save(category);
	}
	
	@Override
	public int update(CategoryDO category){
		return categoryDao.update(category);
	}
	
	@Override
	public int remove(String id){
		return categoryDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return categoryDao.batchRemove(ids);
	}


	
}
