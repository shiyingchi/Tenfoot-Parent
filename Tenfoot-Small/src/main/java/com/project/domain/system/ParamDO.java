package com.project.domain.system;

import java.io.Serializable;
import java.util.Date;



/**
 * 参数表
 * 
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-05 16:13:19
 */

public class ParamDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//参数编码
	private String paramCode;
	//参数名称
	private String paramName;
	//参数值
	private String paramValue;
	//修改时间
	private Date createtime;
	//其他参数(备注、概率)
	private String remark;

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
	 * 设置：参数编码
	 */
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	/**
	 * 获取：参数编码
	 */
	public String getParamCode() {
		return paramCode;
	}
	/**
	 * 设置：参数名称
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	/**
	 * 获取：参数名称
	 */
	public String getParamName() {
		return paramName;
	}
	/**
	 * 设置：参数值
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	/**
	 * 获取：参数值
	 */
	public String getParamValue() {
		return paramValue;
	}
	/**
	 * 设置：修改时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * 设置：其他参数(备注、概率)
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：其他参数(备注、概率)
	 */
	public String getRemark() {
		return remark;
	}
}
