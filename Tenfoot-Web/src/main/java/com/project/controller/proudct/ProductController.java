package com.project.controller.proudct;

import com.project.common.annotation.Log;
import com.project.domain.product.CategoryDO;
import com.project.domain.product.ProductDO;
import com.project.server.product.CategoryService;
import com.project.server.product.ProductService;
import com.project.utils.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/product")
public class ProductController {
	private String prefix = "view/product/";
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping()
	@RequiresPermissions("product:product")
	String Product(){
	    return prefix+"product";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("product:product")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		query.put("userIdCreate", ShiroUtils.getUserId());
		query.put("sort", "gmt_modified");
		query.put("order", "desc");
		List<ProductDO> productList = productService.list(query);
		int total = productService.count(query);
		PageUtils pageUtils = new PageUtils(productList, total);
		return pageUtils;
	}

	@GetMapping("/add")
	@RequiresPermissions("product:add")
	String add(Model model){
		model.addAttribute("categoryList", categoryService.categoryList(ShiroUtils.getUserId()));
		return prefix+"add";
	}

	@GetMapping("/addSpec")//增加规格
	String addSpec(){return prefix+"addSpec";}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("product:edit")
	String edit(@PathVariable("id") String id,Model model){
		ProductDO product = productService.get(id);
		model.addAttribute("product", product);
		model.addAttribute("categoryList", categoryService.categoryList(ShiroUtils.getUserId()));
	    return prefix+"/edit";
	}

	/**
	 * 保存
	 */
	@Log("保存商品")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("product:add")
	public R save(ProductDO product){
		if(productService.save(product)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@Log("修改商品")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("product:edit")
	public R update( ProductDO product){
		productService.update(product);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@Log("删除商品")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("product:remove")
	public R remove( String id){
		if(productService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@Log("批量删除商品")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("product:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		productService.batchRemove(ids);
		return R.ok();
	}


	/**
	 * 导入
	 */
	@GetMapping( "/importProduct")
	@ResponseBody
	public R importProduct(){
		//查询所有分类导入
		Map<String, Object> map = new HashMap<>();
		map.put("userIdCreate", "9");
		List<CategoryDO> categoryDOList = categoryService.list(map);

		for(CategoryDO list : categoryDOList){
			String id = UUIDKey.getUUID();
			CategoryDO categoryDO = list;
			categoryDO.setUserIdCreate(ShiroUtils.getUserId());
			categoryDO.setId(id);
			categoryService.save(categoryDO);
		}

		//导入商品
		List<ProductDO> productList = productService.list(map);
		for(ProductDO list : productList){
			String id = UUIDKey.getUUID();
			ProductDO productDO = list;
			productDO.setUserIdCreate(ShiroUtils.getUserId());
			productDO.setId(id);
			productService.save(productDO);
		}

//		//导入商品分类关联
//		List<ProductCategory> productCategoryList = productService.findProductCategoryList(9L);
//		for(ProductCategory list : productCategoryList){
//			String categoryId = "";
//			String productId = "";
//			for(int i=0;i<categoryArrOld.size();i++){
//				if(list.getCategoryId().equals(categoryArrOld.get(i))){
//					for(int j=0;j<productArrOld.size();j++){
//						if(list.getProductId().equals(productArrOld.get(j))){
//							productId = productArr.get(j);
//							break;
//						}
//					}
//					categoryId = categoryArr.get(i);
//					break;
//				}
//			}
//
//			String id = UUIDKey.getUUID();
//			ProductCategory productCategory = new ProductCategory();
//			productCategory.setId(id);
//			productCategory.setCategoryId(categoryId);
//			productCategory.setProductId(productId);
//			productCategory.setUserId(ShiroUtils.getUserId());
//			productService.categorySave(productCategory);
//		}

		return R.ok();
	}
}
