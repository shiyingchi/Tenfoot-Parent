package com.project.domain.order;

import java.io.Serializable;


/**
 * 订单商品详情
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-10 09:33:15
 */
public class OrderDetialDO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String productName;

	private String spec;

	private String num;

	private Double price;

	private String productSpec;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}
}
