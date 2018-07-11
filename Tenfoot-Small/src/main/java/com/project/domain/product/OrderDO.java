package com.project.domain.product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 订单表
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-08 15:36:27
 */
public class OrderDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//订单号码
	private String id;
	//收货地址id
	private String addressId;
	//门店id
	private Long userId;
	//订单时间
	private Date createTime;
	//用户id
	private String openId;
	//支付订单号
	private String payTradeNo;
	//支付金额
	private Double payAmount;
	//支付成功或失败信息
	private String payDesc;
	//支付时间
	private Date payCreateTime;
	//状态：0待支付，1已支付，2失败，3已配送，4已完成，5已删除
	private String status;
	//预计配送时间
	private String distributionTime;
	//订单完成时间
	private Date orderCompleteTime;
	//配送时间
	private Date distributionOrderTime;
	// 0外卖 1到店
	private String state;

	//购物车列表
	private String cartData;

	//备注
	private String remark;
	//商品数量
	private int num;
	//商品第一张图片
	private String productImage;

	//用户名称
	private String userName;

	//用户地址
	private String address;

	//用户手机号
	private String phone;

	//门店电话
	private String telephone;

	// 用户性别
	private String sex;

	//0未删除 1 已删除
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	List<OrderDetailsDO> orderDetailsDOList;

	public List<OrderDetailsDO> getOrderDetailsDOList() {
		return orderDetailsDOList;
	}

	public void setOrderDetailsDOList(List<OrderDetailsDO> orderDetailsDOList) {
		this.orderDetailsDOList = orderDetailsDOList;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCartData() {
		return cartData;
	}

	public void setCartData(String cartData) {
		this.cartData = cartData;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 设置：订单号码
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：订单号码
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：收货地址id
	 */
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	/**
	 * 获取：收货地址id
	 */
	public String getAddressId() {
		return addressId;
	}
	/**
	 * 设置：门店id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：门店id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：订单时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：订单时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：用户id
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 获取：用户id
	 */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 设置：支付订单号
	 */
	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}
	/**
	 * 获取：支付订单号
	 */
	public String getPayTradeNo() {
		return payTradeNo;
	}
	/**
	 * 设置：支付金额
	 */
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	/**
	 * 获取：支付金额
	 */
	public Double getPayAmount() {
		return payAmount;
	}
	/**
	 * 设置：支付成功或失败信息
	 */
	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}
	/**
	 * 获取：支付成功或失败信息
	 */
	public String getPayDesc() {
		return payDesc;
	}
	/**
	 * 设置：支付时间
	 */
	public void setPayCreateTime(Date payCreateTime) {
		this.payCreateTime = payCreateTime;
	}
	/**
	 * 获取：支付时间
	 */
	public Date getPayCreateTime() {
		return payCreateTime;
	}
	/**
	 * 设置：状态：0待支付，1已支付，2失败，3已配送，4已完成，5已删除
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态：0待支付，1已支付，2失败，3已配送，4已完成，5已删除
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：预计配送时间
	 */
	public void setDistributionTime(String distributionTime) {
		this.distributionTime = distributionTime;
	}
	/**
	 * 获取：预计配送时间
	 */
	public String getDistributionTime() {
		return distributionTime;
	}
	/**
	 * 设置：订单完成时间
	 */
	public void setOrderCompleteTime(Date orderCompleteTime) {
		this.orderCompleteTime = orderCompleteTime;
	}
	/**
	 * 获取：订单完成时间
	 */
	public Date getOrderCompleteTime() {
		return orderCompleteTime;
	}
	/**
	 * 设置：配送时间
	 */
	public void setDistributionOrderTime(Date distributionOrderTime) {
		this.distributionOrderTime = distributionOrderTime;
	}
	/**
	 * 获取：配送时间
	 */
	public Date getDistributionOrderTime() {
		return distributionOrderTime;
	}
}
