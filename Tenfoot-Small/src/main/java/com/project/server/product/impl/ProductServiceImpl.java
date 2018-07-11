package com.project.server.product.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.project.dao.product.ProductDao;
import com.project.dao.system.UserDao;
import com.project.domain.product.ProductCategoryMiddle;
import com.project.domain.system.UserDO;
import com.project.server.product.ProductService;
import com.project.utils.DateTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private UserDao userDao;
	


	/**
	 * 获取店铺商品与分类
	 * @return
	 */
	@Override
//	@Cacheable(value = "findShopProduct", key = "#p0")
	public Map<String,Object>  findShopProduct(Long userId) {
		Map<String,Object> map = new HashMap<>(16);

		if(userId == null){
			map.put("msg","查询失败,店铺ID不能为空");
			map.put("code",0);
			map.put("entity",new JSONObject());
		}else{
			List<ProductCategoryMiddle> list = productDao.findShopProduct(userId);
			JSONArray arr = new JSONArray();
			JSONObject object = new JSONObject();
			if(list.size()>0){
				Set<String> setPcm = new HashSet();
				//循环分类
				for(ProductCategoryMiddle categoryList : list){
					if(!setPcm.contains(categoryList.getCategoryId())){
						JSONObject obj = new JSONObject();
						setPcm.add(categoryList.getCategoryId());
						obj.put("categoryName",categoryList.getCategoryName());
						obj.put("categoryId",categoryList.getCategoryId());
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
				UserDO userDO = userDao.get(userId);
				String businessHours = userDO.getBusinessHours();
				String today = String.valueOf(DateTool.getWeekDayNum(new Date()));
				String nextToday = String.valueOf(DateTool.getWeekDayNum(DateTool.getNDayOfDate(new Date(),1)));
				object.put("shopInfo",userDO);
				object.put("todayOpen",businessHours.contains(today));
				object.put("nextDayOpen",businessHours.contains(nextToday));
				object.put("productInfo",arr);

				map.put("msg","查询成功");
				map.put("code",1);
				map.put("entity",object);
			}else{
				map.put("msg","查询失败,没有商品");
				map.put("code",0);
				map.put("entity",new JSONObject());
			}
		}

		return map;
	}

	@Override
//	@Cacheable(value = "findProductSpec", key = "#p0")
	public Map<String, Object> findProductSpec(String productId) {
		Map<String,Object> map = new HashMap<>(16);

		if(StringUtils.isEmpty(productId)){
			map.put("msg","查询失败,店铺ID不能为空");
			map.put("code",0);
			map.put("entity",new JSONObject());
		}else{
			ProductCategoryMiddle product = productDao.findProductSpec(productId);

			if(product == null){
				map.put("msg","查询失败,没有商品");
				map.put("code",0);
				map.put("entity",new JSONObject());
			}else{
				JSONObject object = new JSONObject();
				object.put("spec",JSONObject.parse(product.getSpec()));
				object.put("productImage",product.getProductImage());
				object.put("productName",product.getProductName());
				object.put("remark",product.getRemark());
				map.put("msg","查询成功");
				map.put("code",1);
				map.put("entity",object);
			}
		}

		return map;
	}

}
