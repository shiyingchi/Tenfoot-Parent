package com.project.server.product.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.project.dao.product.ProductDao;
import com.project.domain.product.ProductCategory;
import com.project.domain.product.ProductCategoryMiddle;
import com.project.domain.product.ProductDO;
import com.project.server.product.ProductService;
import com.project.utils.ShiroUtils;
import com.project.utils.UUIDKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	
	@Override
	public ProductDO get(String id){
		ProductDO productDO= productDao.get(id);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setUserId(ShiroUtils.getUserId());
		productCategory.setProductId(productDO.getId());
		String[] list = productDao.getProductCategory(productCategory);
		productDO.setProductCategoryMiddle(list);
		return productDO;
	}
	
	@Override
	public List<ProductDO> list(Map<String, Object> map){
		return productDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return productDao.count(map);
	}
	
	@Override
	@Transactional
	public int save(ProductDO product){
		product.setId(UUIDKey.getUUID());
		product.setGmtCreate(new Date());
		product.setUserIdCreate(ShiroUtils.getUserId());
		int count = productDao.save(product);
		if(product.getProductCategoryMiddle() != null && product.getProductCategoryMiddle().length>0){
			List<ProductCategory> productCategoryMiddle = new ArrayList<>();
			for(int i=0;i<product.getProductCategoryMiddle().length;i++){
				ProductCategory productCategory = new ProductCategory();
				productCategory.setId(UUIDKey.getUUID());
				productCategory.setProductId(product.getId());
				productCategory.setCategoryId(product.getProductCategoryMiddle()[i]);
				productCategory.setUserId(ShiroUtils.getUserId());
				productCategoryMiddle.add(productCategory);
			}
			productDao.batchSaveProductCategory(productCategoryMiddle);
		}
		return count;
	}
	
	@Override
//	@CacheEvict(value = "findProductSpec")
	public int update(ProductDO product){
		product.setGmtModified(new Date());

		int count = productDao.update(product);
		if(product.getProductCategoryMiddle() != null && product.getProductCategoryMiddle().length>0){
			List<ProductCategory> productCategoryMiddle = new ArrayList<>();
			for(int i=0;i<product.getProductCategoryMiddle().length;i++){
				ProductCategory productCategory = new ProductCategory();
				productCategory.setId(UUIDKey.getUUID());
				productCategory.setProductId(product.getId());
				productCategory.setCategoryId(product.getProductCategoryMiddle()[i]);
				productCategory.setUserId(ShiroUtils.getUserId());
				productCategoryMiddle.add(productCategory);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("productId",product.getId());
			map.put("userId",ShiroUtils.getUserId());
			productDao.batchRemoveProductCategory(map);
			productDao.batchSaveProductCategory(productCategoryMiddle);
		}
		return count;
	}
	
	@Override
	public int remove(String id){
		return productDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return productDao.batchRemove(ids);
	}

	/**
	 * 获取店铺商品与分类
	 * @return
	 */
	@Override
	public Map<String,Object>  findShopProduct(Long userId) {
		Map<String,Object> map = new HashMap<>(16);

		if(userId == null){
			map.put("msg","查询失败,店铺ID不能为空");
			map.put("code",0);
			map.put("entity",new JSONObject());
		}else{
			List<ProductCategoryMiddle> list = productDao.findShopProduct(userId);
			JSONArray arr = new JSONArray();
			if(list.size()>0){
				Set<String> setPcm = new HashSet();
				//循环分类
				for(ProductCategoryMiddle categoryList : list){
					if(!setPcm.contains(categoryList.getCategoryId())){
						JSONObject obj = new JSONObject();
						setPcm.add(categoryList.getCategoryId());
						obj.put("categoryName",categoryList.getCategoryName());
						JSONArray productArr = new JSONArray();
						//循环商品
						for(ProductCategoryMiddle productList : list){
							if(productList.getCategoryId().equals(categoryList.getCategoryId())){
								productArr.add(productList);
							}
						}
						obj.put("productList",productArr);
						arr.add(obj);
					}
				}

				map.put("msg","查询成功");
				map.put("code",1);
				map.put("entity",arr);
			}else{
				map.put("msg","查询失败,没有商品");
				map.put("code",0);
				map.put("entity",new JSONObject());
			}
		}

		return map;
	}

	@Override
	public List<ProductCategory> findProductCategoryList(Long userId) {
		return productDao.findProductCategoryList(userId);
	}

	@Override
	public int categorySave(ProductCategory productCategory) {
		return productDao.categorySave(productCategory);
	}

}
