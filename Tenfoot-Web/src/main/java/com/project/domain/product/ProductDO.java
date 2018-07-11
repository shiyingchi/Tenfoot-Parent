package com.project.domain.product;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author test
 * @email 1@qq.com
 * @date 2017-12-26 15:01:39
 */
public class ProductDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//规格
	private String spec;
	//价格
	private Double price;
	//分类id
	private String[] productCategoryMiddle;
	//商品名称
	private String productName;
	//商品图片
	private String productImage;
	//0下架 1上架 
	private String status;
	//创建时间
	private Date gmtCreate;
	//修改时间
	private Date gmtModified;
	//创建人id
	private Long userIdCreate;
	//备注
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String[] getProductCategoryMiddle() {
		return productCategoryMiddle;
	}

	public void setProductCategoryMiddle(String[] productCategoryMiddle) {
		this.productCategoryMiddle = productCategoryMiddle;
	}

	public Long getUserIdCreate() {
		return userIdCreate;
	}

	public void setUserIdCreate(Long userIdCreate) {
		this.userIdCreate = userIdCreate;
	}


	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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
	 * 设置：商品名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * 获取：商品名称
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * 设置：商品图片
	 */
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	/**
	 * 获取：商品图片
	 */
	public String getProductImage() {
		return productImage;
	}
	/**
	 * 设置：0下架 1上架 
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：0下架 1上架 
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
	/**
	 * 设置：修改时间
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
}
