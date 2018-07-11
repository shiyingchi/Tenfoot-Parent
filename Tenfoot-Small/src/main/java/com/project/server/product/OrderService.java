package com.project.server.product;


import com.project.domain.product.OrderDO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 订单表
 */

public interface OrderService {

    /**
     * 列表
     */
    Map<String,Object> list(Map<String,Object> map);

    /**
     * 详情
     */
    Map<String,Object> findByOrderDetail(String id);

    /**
     * 删除订单
     */
    Map<String,Object> remove(String id);

	//提交订单
    Map<String,Object> weiXinH5Payment(OrderDO orderDO,HttpServletRequest request);

    //统一下单
    Map<String, Object> WeiXinH5Payment(String body,String totalFee,String openid,String number,String orderId,String ip);

    //异步回掉
    String payNotifyUrl(HttpServletRequest request);

    /**
     * 解密
     */
    Map<String, Object> getPhonenumber(String encryptedData,String sessionKey,String iv);

}
