package com.project.domain.product;

import java.io.Serializable;



/**
 * 
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-09 15:37:53
 */
public class OrderDetailsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//
	private String orderId;
	//
	private String productId;
	//规格
	private String spec;
	//价格
	private Double price;
	//数量
	private Integer num;

	private String productName;

	private String productSpec;

	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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
	 * 设置：
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * 设置：
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * 获取：
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 设置：规格
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	/**
	 * 获取：规格
	 */
	public String getSpec() {
		return spec;
	}
	/**
	 * 设置：价格
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * 获取：价格
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * 设置：数量
	 */
	public void setNum(Integer num) {
		this.num = num;
	}
	/**
	 * 获取：数量
	 */
	public Integer getNum() {
		return num;
	}
}
