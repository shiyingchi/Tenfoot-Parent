package com.project.domain.user;

import java.io.Serializable;
import java.util.Date;



/**
 * 会员表
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-05 17:56:44
 */
public class TuserDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String openId;
	//你从
	private String nickName;
	//手机号
	private String phone;
	//用户头像
	private String headImg;
	//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String gender;
	//城市
	private String city;
	//省份
	private String province;
	//国家
	private String country;
	//创建时间
	private Date createTime;
	//登陆时间
	private Date loginTime;
	//店铺id
	private Long userId;

	/**
	 * 设置：
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 获取：
	 */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 设置：你从
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * 获取：你从
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * 设置：手机号
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：手机号
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：用户头像
	 */
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	/**
	 * 获取：用户头像
	 */
	public String getHeadImg() {
		return headImg;
	}
	/**
	 * 设置：用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * 获取：用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * 设置：城市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：城市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：省份
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取：省份
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * 设置：国家
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * 获取：国家
	 */
	public String getCountry() {
		return country;
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
	 * 设置：登陆时间
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	/**
	 * 获取：登陆时间
	 */
	public Date getLoginTime() {
		return loginTime;
	}
	/**
	 * 设置：店铺id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：店铺id
	 */
	public Long getUserId() {
		return userId;
	}
}
