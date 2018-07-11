package com.project.domain.product;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2017-12-26 18:18:09
 */
public class CategoryDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//菜品分类名称
	private String categoryName;
	//状态 0冻结 1上架
	private String status;
	//创建时间
	private Date gmtCreate;
	//创建人id
	private Long userIdCreate;

	public Long getUserIdCreate() {
		return userIdCreate;
	}

	public void setUserIdCreate(Long userIdCreate) {
		this.userIdCreate = userIdCreate;
	}


	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：菜品分类名称
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * 获取：菜品分类名称
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 设置：状态 0冻结 1上架
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态 0冻结 1上架
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：创建时间
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}
}
