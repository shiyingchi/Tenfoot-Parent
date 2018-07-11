package com.project.domain.system;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统用户表
 */
public class UserDO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    // 用户真实姓名
    private String name;
    //头像
    private String headImg;
    //电话
    private String phone;
    //详细地址
    private String address;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //经度
    private Double lat;
    //纬度
    private Double lng;
    //相距多少km
    private Double distance;

    //营业时间
    private String businessHours;

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
