package com.project.controller.order;

import com.project.domain.product.OrderDO;
import com.project.server.product.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by jome on 2018/1/6.
 */

@RequestMapping("/small/order")
@Controller
public class OrderController {

    @Autowired
    OrderService orderService;


    /**
     * 订单列表
     * @param params
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public Map<String,Object> list(@RequestParam Map<String, Object> params){
        return orderService.list(params);
    }

    @PostMapping( "/weiXinH5Payment")
    @ResponseBody
    public Map<String,Object> weiXinH5Payment(OrderDO orderDO,HttpServletRequest request){
        return orderService.weiXinH5Payment(orderDO,request);
    }

    /**
     * 订单详情
     */
    @ResponseBody
    @GetMapping("/findByOrderDetail")
    public Map<String,Object> findByOrderDetail(String id){
        return orderService.findByOrderDetail(id);
    }

    /**
     * 删除订单
     */
    @ResponseBody
    @GetMapping("/remove")
    public Map<String,Object> remove(String id){
        return orderService.remove(id);
    }


    String shaxiangKey = "";
    /**
     * 对账下载
     */
//    @ResponseBody
//    @GetMapping("/downloadbill")
//    Map<String, Object> downloadbill() throws Exception{
//        String nonceStr1 = UUID.randomUUID().toString().replaceAll("-", "");
//        SortedMap<String, String> parameters1=new TreeMap<>();
//        parameters1.put("mch_id", Constant.PARAM_MAP.get("mchid"));//公众账号ID
//        parameters1.put("nonce_str", nonceStr1);//商品描述
//        String sign1 = RSASignatureUtil.createSign(parameters1, Constant.PARAM_MAP.get("key"));
//        String xml1= XMLUtil.BuildXML(parameters1, sign1);
//        System.out.println(xml1);
//        String postResult1 = HttpClientUtil.postXml("https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey",xml1);
//        System.out.println(postResult1);
//        Map<String, String> responseMap1 =XMLUtil.parseXml(postResult1);
//        System.out.println(responseMap1);
//
//        shaxiangKey = responseMap1.get("sandbox_signkey");
//
//        System.out.println(shaxiangKey);
//        // 随机数
//        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
//        SortedMap<String, String> parameters=new TreeMap<>();
//        parameters.put("appid", Constant.PARAM_MAP.get("appid"));//公众账号ID
//        parameters.put("mch_id", Constant.PARAM_MAP.get("mchid"));//商户号
//        parameters.put("nonce_str", nonceStr);//随机字符串
//        parameters.put("bill_date", "20180116");//用户标识
//        parameters.put("bill_type", "ALL");//附加数据
////			String sign = RSASignatureUtil.createSign(parameters, Constant.PARAM_MAP.get("key"));
//        String sign = RSASignatureUtil.createSign(parameters, shaxiangKey);
//        String xml= XMLUtil.BuildXML(parameters, sign);
//        System.out.println(xml);
//        String postResult = HttpClientUtil.postXml("https://api.mch.weixin.qq.com/sandboxnew/pay/downloadbill",xml);
//        System.out.println("=====postResult"+postResult);
//
//
//
//
//        return R.hashMapOk("11",null);
//    }




    /**
     * 微信支付异步
     */
    @RequestMapping("/weixinH5Callback")
    @ResponseBody
    public String payNotifyUrl(HttpServletRequest request) throws Exception {
        return orderService.payNotifyUrl(request);
    }

    /**
     * 解密获取手机号
     */
    @ResponseBody
    @GetMapping(value="/getPhonenumber")
    Map<String,Object> getPhonenumber(String encryptedData,String sessionKey,String iv){
        return orderService.getPhonenumber(encryptedData,sessionKey,iv);
    }


}
