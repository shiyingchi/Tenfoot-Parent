package com.project.domain.user;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-06 19:14:52
 */
public class AddressDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//地址
	private String address;
	//用户id
	private String openId;
	//姓名
	private String userName;
	//电话
	private String phone;
	//创建时间
	private Date createTime;
	//状态：0删除，1正常
	private String status;
	//性别：0女，1男
	private String sex;
	//用户选择的地址：默认0，选中1
	private String state;
	//门店id
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
	 * 设置：地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：地址
	 */
	public String getAddress() {
		return address;
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
	 * 设置：姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：姓名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：状态：0删除，1正常
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态：0删除，1正常
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：用户选择的地址：默认0，选中1
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * 获取：用户选择的地址：默认0，选中1
	 */
	public String getState() {
		return state;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}
