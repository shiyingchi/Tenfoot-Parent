package com.project.server.order.impl;

import com.alibaba.druid.util.StringUtils;
import com.project.dao.order.OrderDao;
import com.project.domain.order.OrderDO;
import com.project.domain.order.OrderDetialDO;
import com.project.server.order.OrderService;
import com.project.utils.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.*;




/**
 * 订单表
 *
 * @author jome
 * @email 925259117@qq.com
 * @date 2018-01-10 09:33:15
 */


@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;

	@Override
	public OrderDO get(String id){
		OrderDO orderDO = orderDao.get(id);
		List<OrderDetialDO> orderDetialDOList = orderDao.getOrderProductList(id);
		if(orderDetialDOList.size()>0){
			for(OrderDetialDO list : orderDetialDOList){
				String spec = "";
				String[] specList = list.getSpec().split(",");
				JSONObject object = JSONObject.fromObject(list.getProductSpec());
				JSONArray arr = JSONArray.fromObject(object.get("smallData"));
				if(arr.size()>0){
					for(int i=0;i<arr.size();i++){
						JSONObject o = JSONObject.fromObject(arr.get(i));
						spec += o.get("specName")+":"+specList[i]+"、";
					}
				}
				list.setSpec(spec);
			}
		}
		orderDO.setOrderDetialDOList(orderDetialDOList);
		return orderDO;
	}
	
	@Override
	public List<OrderDO> list(Map<String, Object> map){
		return orderDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return orderDao.count(map);
	}
	
	@Override
	public int save(OrderDO order){
		return orderDao.save(order);
	}
	
	@Override
	public int update(OrderDO order){
		return orderDao.update(order);
	}
	
	@Override
	@Transactional
	public int remove(String id){
		//删除了订单 跟着删除订单明细
		orderDao.remove(id);
		return orderDao.removeDetail(id);
	}
	
	@Override
	@Transactional
	public int batchRemove(String[] ids){
		orderDao.batchRemove(ids);
		return orderDao.batchRemoveDetail(ids);
	}

	@Override
	public int orderDistribution(String id) {
		OrderDO orderDO = orderDao.get(id);
		orderDO.setStatus("3");
		orderDO.setDistributionOrderTime(DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
		return orderDao.update(orderDO);
	}

	@Override
	public int orderComplete(String id) {
		OrderDO orderDO = orderDao.get(id);
		orderDO.setStatus("4");
		orderDO.setOrderCompleteTime(DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
		return orderDao.update(orderDO);
	}


	String shaxiangKey = "";
	@Override
	public Map<String,Object> orderRefund(String id, HttpServletRequest request) {
		try {
			OrderDO orderDO = orderDao.get(id);
			if(orderDO == null){
				return R.hashMapError("退款失败,无效订单");
			}else {
//
//				System.out.println("/*************************************************************************************************************/");
//
//				String nonceStr1 = UUID.randomUUID().toString().replaceAll("-", "");
//				SortedMap<String, String> parameters1=new TreeMap<>();
//				parameters1.put("mch_id", Constant.PARAM_MAP.get("mchid"));//公众账号ID
//				parameters1.put("nonce_str", nonceStr1);//商品描述
//				String sign1 = RSASignatureUtil.createSign(parameters1, Constant.PARAM_MAP.get("key"));
//				String xml1= XMLUtil.BuildXML(parameters1, sign1);
//				System.out.println(xml1);
//				String postResult1 = HttpClientUtil.postXml("https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey",xml1);
//				System.out.println(postResult1);
//				Map<String, String> responseMap1 =XMLUtil.parseXml(postResult1);
//				System.out.println(responseMap1);
//
//				System.out.println("/*************************************************************************************************************/");
//
//				shaxiangKey = responseMap1.get("sandbox_signkey");
//
//				System.out.println(shaxiangKey);

				// 随机数
				String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
				SortedMap<String, String> parameters = new TreeMap<>();
				parameters.put("appid", Constant.PARAM_MAP.get("appid"));
				parameters.put("mch_id", Constant.PARAM_MAP.get("mchid"));
				parameters.put("nonce_str", nonceStr);
				parameters.put("out_trade_no", id); //商户系统内部的订单号
				parameters.put("out_refund_no",id);
				parameters.put("total_fee", Math.round(orderDO.getPayAmount()*100)+"");
				parameters.put("refund_fee", Math.round(orderDO.getPayAmount()*100) + "");
//				String sign = RSASignatureUtil.createSign(parameters, shaxiangKey);
				String sign = RSASignatureUtil.createSign(parameters, Constant.PARAM_MAP.get("key"));
				String xml = XMLUtil.BuildXML(parameters, sign);

				String postResult = doRefund(xml);
				Map<String, String> responseMap = XMLUtil.parseXml(postResult);
				System.out.println("responseMap:"+responseMap);

				if (!"SUCCESS".equals(responseMap.get("return_code"))) {
					System.out.println("退款失败：" + responseMap.get("return_msg"));
					orderDO.setStatus("8");
					orderDao.update(orderDO);
					return R.hashMapError("申请退款失败");
				} else if ("SUCCESS".equals(responseMap.get("return_code"))) {
					orderDO.setStatus("7");
					orderDao.update(orderDO);
					return R.hashMapOk("申请退款成功",null);
				} else {
					orderDO.setStatus("8");
					orderDao.update(orderDO);
					return R.hashMapError("申请退款失败");
				}

			}
		}catch (Exception e){
			e.printStackTrace();
			return R.hashMapError("申请退款失败");
		}

	}

	private String doRefund(String data) throws Exception {
		/*
         * <xml>
               <appid>wx2421b1c4370ec43b</appid>
               <mch_id>10000100</mch_id>
               <nonce_str>6cefdb308e1e2e8aabd48cf79e546a02</nonce_str>
               <op_user_id>10000100</op_user_id>
               <out_refund_no>1415701182</out_refund_no>
               <out_trade_no>1415757673</out_trade_no>
               <refund_fee>1</refund_fee>
               <total_fee>1</total_fee>
               <transaction_id></transaction_id>
               <sign>FE56DD4AA85C0EECA82C35595A69E153</sign>
            </xml>
         */

		/**
		 * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
		 */

		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
		//P12文件目录
		FileInputStream instream = new FileInputStream(new File(Constant.PARAM_MAP.get("certMD")));
		try {
			//这里写密码..默认是你的MCHID
			keyStore.load(instream, Constant.PARAM_MAP.get("mchid").toCharArray());
		} finally {
			instream.close();
		}

			//这里也是写密码的
		SSLContext sslcontext = SSLContexts.custom()
				.loadKeyMaterial(keyStore, Constant.PARAM_MAP.get("mchid").toCharArray())
				.build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext,
				new String[] { "TLSv1" },
				null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.build();
		try {
			// 设置响应头信息
			HttpPost httpost = new HttpPost(Constant.unifiedorderUrl);
			httpost.addHeader("Connection", "keep-alive");
			httpost.addHeader("Accept", "*/*");
			httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpost.addHeader("Host", "api.mch.weixin.qq.com");
			httpost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpost.addHeader("Cache-Control", "max-age=0");
			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
			httpost.setEntity(new StringEntity(data, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpost);
			try {
				HttpEntity entity = response.getEntity();

				String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
				EntityUtils.consume(entity);
				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}


	/**
	 * 每5分钟扫描订单 自动完成
	 */
	@Override
	public void updateOrder() {
		List<OrderDO> list = orderDao.findOrderList();
		if(list.size()>0){
			List<String> ids = new ArrayList<>();
			for(int i=0;i<list.size();i++){
				OrderDO orderDO = list.get(i);
				String str = DateUtils.getTimeBeforeAccurate(DateTool.getDateFormat(orderDO.getDistributionOrderTime(),5));
				//判断是否包含分字 有则截取分前面的字符串
				if(str.contains("分")){
					str = str.substring(0,str.indexOf("分"));
					//判断是否包含天字  有则截取天后面的字符串
					if(str.contains("天")){
						str = str.substring(str.indexOf("天")+1,str.length());
					}
				}
				//去掉前字
				if(!StringUtils.isEmpty(str.substring(0,str.length()-1))){
					// 大于30分中自动完成订单
					if(Integer.parseInt(str) > 30){
						ids.add(orderDO.getId());
					}
				}

			}
			if(ids.size()>0){
				orderDao.updateOrder(ids);
			}
			System.out.println("处理了"+ids.size()+"条数据");
		}
	}

	public static void main(String[] args) {
		String str = "";
		if(str.contains("分")){
			str = str.substring(0,str.indexOf("分"));
			if(str.contains("天")){
				str = str.substring(str.indexOf("天")+1,str.length());
			}
		}
		System.out.println(str);
	}
	private String returnXML(String return_code,String msg) {

		return "<xml><return_code><![CDATA["

				+ return_code

				+ "]]></return_code><return_msg><![CDATA["
				+ msg
				+"]]></return_msg></xml>";
	}

	public String getSign(Map<String, String> map) {
		SortedMap<String, String> signParams = new TreeMap<String, String>();
		for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
			signParams.put(stringStringEntry.getKey(), stringStringEntry.getValue());
		}
		signParams.remove("sign");
		String sign = RSASignatureUtil.createSign(signParams, Constant.PARAM_MAP.get("key"));
		return sign;
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

}
