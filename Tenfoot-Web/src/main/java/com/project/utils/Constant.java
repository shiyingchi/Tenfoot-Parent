package com.project.utils;

import java.util.HashMap;


/**
 * 常量类
 * @author  jome
 */
public class Constant {
    //演示系统账户
    public static String DEMO_ACCOUNT = "test";
    //自动去除表前缀
    public static String AUTO_REOMVE_PRE = "true";
    //停止计划任务
    public static String STATUS_RUNNING_STOP = "stop";
    //开启计划任务
    public static String STATUS_RUNNING_START = "start";

    /** 项目名称 **/
    public static final String PROJECT_NAME = "/Tenfoot-Web";

    //用户登录次数计数  redisKey 前缀
    public static final String SHIRO_LOGIN_COUNT = "shiro_login_count_";

    //用户登录是否被锁定    一小时 redisKey 前缀
    public static final String SHIRO_IS_LOCK = "shiro_is_lock_";


    /** 缓存名称 **/
    public static final String CACHE_NAME = "role";

    /**配置参数**/
    public static HashMap<String,String> PARAM_MAP = new HashMap();

    /** 头像图片路径 **/
    public static String HEAD_IMG = "images/head/";
    /** 商品图片路径 **/
    public static String PRODUCT_IMG = "images/product/";
    /** 商品分类图片路径 **/
    public static String PRODUCT_CATEGORY_IMG = "images/productCategory/";

    /**
     * 申请退款
     */
    public static String unifiedorderUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";
//    public static String unifiedorderUrl = "https://api.mch.weixin.qq.com/sandboxnew/pay/refund";
    public static String payUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
