package com.project.controller.order;

import com.project.utils.*;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.*;

/**
 * Created by jome on 2018/1/6.
 */

@RequestMapping("/test")
@Controller
public class TestController {


    @GetMapping()
//    String test(){
//        return   "/test";
//    }
    String test(){
        return   "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx57d3f3924db043de&redirect_uri=https%3A%2F%2Ft.fmsecret.cn%2FTenfoot-Web%2Ftest&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
    }

    /**
     * 微信内H5调起支付
     *
     */
    @ResponseBody
    @PostMapping("/pay")
    public Map<String,Object> test(HttpServletRequest request,String openId) throws Exception{



        // 随机数
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        SortedMap<String, String> parameters=new TreeMap<>();
        parameters.put("appid", Constant.PARAM_MAP.get("appid"));
        parameters.put("body", "测试支付");
        parameters.put("mch_id", Constant.PARAM_MAP.get("mchid"));
        parameters.put("nonce_str", nonceStr);
        parameters.put("notify_url", "https://t.fmsecret.cn/Tenfoot-Web/test/notify");
        parameters.put("out_trade_no", UUIDKey.getUUIDNO());
        parameters.put("spbill_create_ip", getIpAddr(request));
        parameters.put("sub_mch_id", Constant.PARAM_MAP.get("submchid"));
        parameters.put("sub_openid", openId);

        parameters.put("total_fee", "1");
        parameters.put("trade_type", "JSAPI");

        String sign = RSASignatureUtil.createSign(parameters, Constant.PARAM_MAP.get("subkey"));
        String xml= XMLUtil.BuildXML(parameters, sign);
        System.out.println(xml);
        String postResult = HttpClientUtil.postXml(Constant.payUrl,xml);
        System.out.println(postResult);
        Map<String, String> responseMap =XMLUtil.parseXml(postResult);
        System.out.println(responseMap);

        if (!"SUCCESS".equals(responseMap.get("return_code"))) {
            System.out.println("下单失败：" + responseMap.get("return_msg"));
            return R.hashMapError("下单失败");
        }else if("SUCCESS".equals(responseMap.get("return_code"))){
            String noncestr = UUID.randomUUID().toString();
            noncestr = noncestr.replaceAll("-", "").toUpperCase();
            long timeStamp = System.currentTimeMillis()/1000;
            String paySign = signatureJS(responseMap.get("appid"), timeStamp, noncestr, responseMap.get("prepay_id"),  Constant.PARAM_MAP.get("subkey"));
            JSONObject dto = new JSONObject();
            dto.put("appid",responseMap.get("appid"));
            dto.put("nonceStr",noncestr);
            dto.put("paySign",paySign);
            dto.put("prepayId",responseMap.get("prepay_id"));
            dto.put("timeStamp",timeStamp);
            return R.hashMapOk("下单成功",dto);
        }else{
            System.out.println(responseMap.get("return_msg"));
            return R.hashMapError("下单失败");
        }
    }

//
//    @ResponseBody
//    @PostMapping(value={"/pay","/testpay"})
//    public Map<String,Object> test(HttpServletRequest request,String openId) throws Exception{
//        // 随机数
//        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
//        SortedMap<String, String> parameters=new TreeMap<>();
//        parameters.put("appid", Constant.PARAM_MAP.get("appid"));
//        parameters.put("body", "测试支付");
//        parameters.put("mch_id", Constant.PARAM_MAP.get("mchid"));
//        parameters.put("nonce_str", nonceStr);
//        parameters.put("openid", openId);
//        parameters.put("out_trade_no", UUIDKey.getUUIDNO());
//        parameters.put("total_fee", "1");
//        parameters.put("trade_type", "JSAPI");
//        parameters.put("device_info", "WEB");
//        parameters.put("notify_url", "https://t.fmsecret.cn/Tenfoot-Small/test/notify");
//
//
//        String sign = RSASignatureUtil.createSign(parameters, Constant.PARAM_MAP.get("key"));
//        String xml= XMLUtil.BuildXML(parameters, sign);
//        System.out.println(xml);
//        String postResult = HttpClientUtil.postXml(Constant.payUrl,xml);
//        System.out.println(postResult);
//        Map<String, String> responseMap =XMLUtil.parseXml(postResult);
//        System.out.println(responseMap);
//
//        if (!"SUCCESS".equals(responseMap.get("return_code"))) {
//            System.out.println("下单失败：" + responseMap.get("return_msg"));
//            return R.hashMapError("下单失败");
//        }else if("SUCCESS".equals(responseMap.get("return_code"))){
//            String noncestr = UUID.randomUUID().toString();
//            noncestr = noncestr.replaceAll("-", "").toUpperCase();
//            long timeStamp = System.currentTimeMillis()/1000;
//            String paySign = signatureJS(responseMap.get("appid"), timeStamp, noncestr, responseMap.get("prepay_id"),  Constant.PARAM_MAP.get("key"));
//            JSONObject dto = new JSONObject();
//            dto.put("appid",responseMap.get("appid"));
//            dto.put("nonceStr",noncestr);
//            dto.put("paySign",paySign);
//            dto.put("prepayId",responseMap.get("prepay_id"));
//            dto.put("timeStamp",timeStamp);
//            return R.hashMapOk("下单成功",dto);
//        }else{
//            System.out.println(responseMap.get("return_msg"));
//            return R.hashMapError("下单失败");
//        }
//    }


    /**
     * 微信支付异步
     */
    @RequestMapping("/weixinH5Callback")
    @ResponseBody
    public String payNotifyUrl(HttpServletRequest request) throws Exception {
        try{
            BufferedReader reader = null;
            reader = request.getReader();
            String line = "";
            String xmlString = null;
            StringBuffer inputString = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                inputString.append(line);
            }
            xmlString = inputString.toString();
            request.getReader().close();
            Map<String, String> map = new HashMap<String, String>();
            map = XMLUtil.parseXml(xmlString);
            System.out.println(map);

        }catch(Exception e){
            System.out.println("处理业务失败！");
            return "";
        }

        return "";
    }

    /**
     * 生成签名（JS调用支付）
     */
    public String signatureJS(String appid, long timeStamp, String noncestr, String prepayid, String myKey) {
        SortedMap<String, String> parameters=new TreeMap<>();
        parameters.put("appId", appid);
        parameters.put("nonceStr",noncestr);
        parameters.put("package", "prepay_id="+prepayid);
        parameters.put("signType", "MD5");
        parameters.put("timeStamp", timeStamp+"");
        String sign=RSASignatureUtil.createSign(parameters,myKey);
        return sign.toUpperCase();
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(ip!=null && !"unKnown".equalsIgnoreCase(ip)){
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(ip!=null && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }



    @RequestMapping("/notify")
    @ResponseBody
    public String notify(HttpServletRequest request) throws Exception {
        try{
            BufferedReader reader = null;
            reader = request.getReader();
            String line = "";
            String xmlString = null;
            StringBuffer inputString = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                inputString.append(line);
            }
            xmlString = inputString.toString();
            request.getReader().close();
            Map<String, String> map = new HashMap<String, String>();
            map = XMLUtil.parseXml(xmlString);

            System.out.println(map);
            return "";

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("处理业务失败！");
            return "";
        }
    }



    @ResponseBody
    @PostMapping("/getUserInfo")
    public Map<String,Object> getUserInfo(String code) throws Exception{
        String token = "";
        String oppenId = "";
        String refresh_token = "";
        JSONObject jobjD = new JSONObject();


        String jsonD = HTTPRequestUtil.doGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+Constant.PARAM_MAP.get("appid")+"&secret="+Constant.PARAM_MAP.get("secret")+"&code="+code+"&grant_type=authorization_code");


        System.out.println("jsonD"+jsonD);
        if (!jsonD.contains("errcode")) {
            jobjD = JSONObject.fromObject(jsonD);
            token = jobjD.getString("access_token");
            refresh_token = jobjD.getString("refresh_token");

            //刷新token
            System.out.println("jsonD2"+jsonD);
            jsonD = HTTPRequestUtil.doGet("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+Constant.PARAM_MAP.get("appid")+"&grant_type=refresh_token&refresh_token="+refresh_token);
            if (!jsonD.contains("errcode")) {
                jobjD = JSONObject.fromObject(jsonD);
                token = jobjD.getString("access_token");
                oppenId = jobjD.getString("openid");
//                CacheKit.put("code2", code, code);
            }

        }

        return R.hashMapOk("",oppenId);

    }


}
