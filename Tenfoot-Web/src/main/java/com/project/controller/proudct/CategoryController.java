package com.project.controller.proudct;

import com.project.common.annotation.Log;
import com.project.domain.product.CategoryDO;
import com.project.server.product.CategoryService;
import com.project.utils.PageUtils;
import com.project.utils.Query;
import com.project.utils.R;
import com.project.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2017-12-26 18:18:09
 */
 
@Controller
@RequestMapping("/category")
public class CategoryController {
	private String prefix = "view/category/";

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping()
	@RequiresPermissions("category:category")
	String Category(){
	    return prefix+"category";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("category:category")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		query.put("userIdCreate", ShiroUtils.getUserId());
		List<CategoryDO> categoryList = categoryService.list(query);
		int total = categoryService.count(query);
		PageUtils pageUtils = new PageUtils(categoryList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("category:add")
	String add(){
	    return prefix+"add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("category:edit")
	String edit(@PathVariable("id") String id,Model model){
		CategoryDO category = categoryService.get(id);
		model.addAttribute("category", category);
	    return prefix+"edit";
	}
	
	/**
	 * 保存
	 */
	@Log("保存菜单分类")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("category:add")
	public R save(CategoryDO category){
		if(categoryService.save(category)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@Log("修改菜单分类")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("category:edit")
	public R update( CategoryDO category){
		categoryService.update(category);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@Log("删除菜单分类")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("category:remove")
	public R remove( String id){
		if(categoryService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@Log("批量删除菜单分类")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("category:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids){
		categoryService.batchRemove(ids);
		return R.ok();
	}


	/**
	 * 获取商品分类
	 */
	@GetMapping("/categoryList")
	@ResponseBody()
	List<CategoryDO> categoryList() {
		return categoryService.categoryList(ShiroUtils.getUserId());
	}
}
