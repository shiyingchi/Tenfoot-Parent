package com.project.server.product.impl;

import com.project.dao.product.OrderDao;
import com.project.dao.system.UserDao;
import com.project.domain.product.OrderDO;
import com.project.domain.product.OrderDetailsDO;
import com.project.domain.system.UserDO;
import com.project.server.product.OrderService;
import com.project.utils.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.security.AlgorithmParameters;
import java.security.KeyStore;
import java.security.Security;
import java.util.*;


//@Service
public class shaxiang implements OrderService {
	/**并发锁**/
	public static Object lock = new Object();


	@Autowired
	OrderDao orderDao;

	@Autowired
	UserDao userDao;

	@Override
//	@Cacheable(value = "order_list", key = "#p0")
	public Map<String, Object> list(Map<String, Object> map) {
		if(StringUtils.isEmpty(map.get("userId").toString())){
			return R.hashMapError("门店id不能为空");
		}else if(StringUtils.isEmpty(map.get("openId").toString())){
			return R.hashMapError("用户id不能为空");
		}else{
			map.put("sort","o.create_time");
			map.put("order","desc");
			return R.hashMapOk("查询成功",orderDao.list(map));
		}
	}

	@Override
//	@Cacheable(value = "order_findByOrderDetail", key = "#p0")
	public Map<String, Object> findByOrderDetail(String id) {
		if(StringUtils.isEmpty(id)){
			return R.hashMapError("订单id不能为空");
		}else{
			OrderDO orderDO = orderDao.get0(id);
			if(orderDO == null){
				orderDO = orderDao.get1(id);
			}
			List<OrderDetailsDO> orderDetialDOList = orderDao.getOrderProductList(id);
			if(orderDetialDOList.size()>0){
				for(OrderDetailsDO list : orderDetialDOList){
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
			orderDO.setOrderDetailsDOList(orderDetialDOList);

			return R.hashMapOk("查询成功",orderDO);
		}
	}

	@Override
//	@CacheEvict(value = "order_list")
	public Map<String, Object> remove(String id) {
		OrderDO orderDO = orderDao.get0(id);
		if(orderDO == null){
			orderDO = orderDao.get1(id);
		}
		if(orderDO == null){
			return R.hashMapError("查询失败");
		}else{
			orderDO.setType("1");
			orderDao.update(orderDO);
			return R.hashMapOk("删除成功",null);
		}
	}

	/**
	 * 提交订单
	 * @return
	 */
	@Transactional
//	@CacheEvict(value = "order_list")
	@Override
	public Map<String, Object> weiXinH5Payment(OrderDO orderDO,HttpServletRequest request) {
		synchronized (lock){
			try {
				//id为空则下单支付   不为空则重新发起
				if(StringUtils.isEmpty(orderDO.getId())){
					JSONArray arr = JSONArray.fromObject(orderDO.getCartData());
					if(orderDO.getUserId() == null){
						return R.hashMapError("门店不能为空,下单失败");
					}else if(StringUtils.isEmpty(orderDO.getOpenId())){
						return R.hashMapError("用户不能为空");
					}else if(orderDO.getPayAmount() == null){
						return R.hashMapError("订单价格不能为空");
					}else if(arr.size()<=0){
						return R.hashMapError("购物车不能为空");
					}else{
						//判断所选规格是否异常
						Double sum = 0.00d;
						int number = 0;
						String id = UUIDKey.getUUIDNO();
						List<OrderDetailsDO> list = new ArrayList<>();
						for(int i=0;i<arr.size();i++){
							JSONObject object = JSONObject.fromObject(arr.get(i));
							OrderDetailsDO orderDetailsDO = new OrderDetailsDO();
							orderDetailsDO.setId(UUIDKey.getUUID());
							orderDetailsDO.setOrderId(id);
							orderDetailsDO.setProductId(object.getString("foodId"));
							orderDetailsDO.setSpec(object.getString("spec"));
							orderDetailsDO.setNum(object.getInt("num"));
							orderDetailsDO.setPrice(Double.valueOf(object.getString("singleSum")));
							list.add(orderDetailsDO);
							sum += Double.valueOf(object.getString("singleSum"));
							number += object.getInt("num");
						}
						if(sum.equals(orderDO.getPayAmount())){
							//保存订单详情
							orderDao.batchSave(list);
							orderDO.setId(id);
							orderDO.setStatus("0");
							orderDO.setNum(number);
							orderDO.setCreateTime(new Date());
							orderDao.save(orderDO);

							UserDO userDO = userDao.get(orderDO.getUserId());

							return WeiXinH5Payment(userDO.getName(), (int)(sum*100)+"", orderDO.getOpenId(), orderDO.getUserId()+"",orderDO.getId(),getIpAddr(request));

						}else{
							return R.hashMapError("价格异常");
						}
					}
				}else{
					String id = orderDO.getId();
					orderDO = orderDao.get0(id);
					if(orderDO == null){
						orderDO = orderDao.get1(id);
					}

					UserDO userDO = userDao.get(orderDO.getUserId());

					return WeiXinH5Payment(userDO.getName(), (int)(orderDO.getPayAmount()*100)+"", orderDO.getOpenId(), orderDO.getUserId()+"",orderDO.getId(),getIpAddr(request));
				}

			}catch (Exception e){
				e.printStackTrace();
				return R.hashMapError("订单异常,下单失败");
			}

		}

	}


	String shaxiangKey = "";

	/**
	 * 统一下单
	 */
	@Override
	public Map<String, Object> WeiXinH5Payment(String body,String totalFee,String openid,String number,String orderId,String ip) {

		try{
			if(body==null || body.length()==0 || totalFee==null || totalFee.length()==0 || openid==null || openid.length()==0 ||number==null || number.length()==0){
				return R.hashMapError("请求参数为null或值为空字符");
			}
			System.out.println("/******************获取key****************************************************************************************/");

			String nonceStr1 = UUID.randomUUID().toString().replaceAll("-", "");
			SortedMap<String, String> parameters1=new TreeMap<>();
			parameters1.put("mch_id", Constant.PARAM_MAP.get("mchid"));//公众账号ID
			parameters1.put("nonce_str", nonceStr1);//商品描述
			String sign1 = RSASignatureUtil.createSign(parameters1, Constant.PARAM_MAP.get("key"));
			String xml1= XMLUtil.BuildXML(parameters1, sign1);
			System.out.println(xml1);
			String postResult1 = HttpClientUtil.postXml("https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey",xml1);
			System.out.println(postResult1);
			Map<String, String> responseMap1 =XMLUtil.parseXml(postResult1);
			System.out.println(responseMap1);

			System.out.println("/*********************支付**************************************************************************************/");

			shaxiangKey = responseMap1.get("sandbox_signkey");

			System.out.println(shaxiangKey);
			// 随机数
			String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
			SortedMap<String, String> parameters=new TreeMap<>();
			parameters.put("appid", Constant.PARAM_MAP.get("appid"));//公众账号ID
			parameters.put("body", body);//商品描述
			parameters.put("mch_id", Constant.PARAM_MAP.get("mchid"));//商户号
			parameters.put("nonce_str", nonceStr);//随机字符串
			parameters.put("openid", openid);//用户标识
			parameters.put("attach", number);//附加数据
			parameters.put("out_trade_no", orderId); //商户系统内部的订单号
			parameters.put("spbill_create_ip", ip);//终端IP
			parameters.put("total_fee", "552");//标价金额
//			parameters.put("total_fee", totalFee);//标价金额
			parameters.put("trade_type", "JSAPI");//交易类型
			parameters.put("notify_url", Constant.PARAM_MAP.get("weixinH5NotifyUrl"));//回调地址
//			String sign = RSASignatureUtil.createSign(parameters, Constant.PARAM_MAP.get("key"));
			String sign = RSASignatureUtil.createSign(parameters, shaxiangKey);
			String xml= XMLUtil.BuildXML(parameters, sign);
			System.out.println(xml);
			String postResult = HttpClientUtil.postXml(Constant.unifiedorderUrl,xml);
			System.out.println(postResult);
			Map<String, String> responseMap =XMLUtil.parseXml(postResult);
			System.out.println(responseMap);



			System.out.println("/************订单查询*************************************************************************************************/");

			nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
			parameters=new TreeMap<>();
			parameters.put("appid", Constant.PARAM_MAP.get("appid"));//公众账号ID
			parameters.put("mch_id", Constant.PARAM_MAP.get("mchid"));//商户号
			parameters.put("nonce_str", nonceStr);//随机字符串
			parameters.put("out_trade_no", orderId);//用户标识
			sign = RSASignatureUtil.createSign(parameters, shaxiangKey);
			xml= XMLUtil.BuildXML(parameters, sign);
			System.out.println(xml);
			postResult = HttpClientUtil.postXml("https://api.mch.weixin.qq.com/sandboxnew/pay/orderquery",xml);
			System.out.println(postResult);
			responseMap =XMLUtil.parseXml(postResult);
			System.out.println(responseMap);

			System.out.println("/************退款***********************************************************************************************/");

			// 随机数
			nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
			parameters = new TreeMap<>();
			parameters.put("appid", Constant.PARAM_MAP.get("appid"));
			parameters.put("mch_id", Constant.PARAM_MAP.get("mchid"));
			parameters.put("nonce_str", nonceStr);
			parameters.put("out_trade_no", orderId); //商户系统内部的订单号
			parameters.put("out_refund_no",orderId);
			parameters.put("total_fee",  "552");
//							parameters1.put("total_fee", o.getPayAmount() +"");
//							parameters1.put("refund_fee", o.getPayAmount() + "");
			parameters.put("refund_fee",  "552");
			sign = RSASignatureUtil.createSign(parameters, shaxiangKey);
			xml = XMLUtil.BuildXML(parameters, sign);

			postResult = doRefund(xml);
			responseMap = XMLUtil.parseXml(postResult);
			System.out.println("responseMap:"+responseMap);

			System.out.println("/*****************退款查询******************************************************************************************/");

			nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
			parameters=new TreeMap<>();
			parameters.put("appid", Constant.PARAM_MAP.get("appid"));//公众账号ID
			parameters.put("mch_id", Constant.PARAM_MAP.get("mchid"));//商户号
			parameters.put("nonce_str", nonceStr);//随机字符串
			parameters.put("out_trade_no", orderId);//用户标识
			sign = RSASignatureUtil.createSign(parameters, shaxiangKey);
			xml= XMLUtil.BuildXML(parameters, sign);
			postResult = HttpClientUtil.postXml("https://api.mch.weixin.qq.com/sandboxnew/pay/refundquery",xml);
			responseMap =XMLUtil.parseXml(postResult);

			System.out.println(responseMap);

			System.out.println("/*************************************************************************************************************/");





			if (!"SUCCESS".equals(responseMap.get("return_code"))) {
				System.out.println("下单失败：" + responseMap.get("return_msg"));
				return R.hashMapError("下单失败");
			}else if("SUCCESS".equals(responseMap.get("return_code"))){
				String noncestr = UUID.randomUUID().toString();
				noncestr = noncestr.replaceAll("-", "").toUpperCase();
				long timeStamp = System.currentTimeMillis()/1000;
				String paySign = signatureJS(responseMap.get("appid"), timeStamp, noncestr, responseMap.get("prepay_id"),  Constant.PARAM_MAP.get("key"));
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
		}catch(Exception e){
			e.printStackTrace();
			return R.hashMapError("下单失败");
		}

	}

	/**
	 * 异步回调
	 * @param request
	 * @return
	 */
	@Override
	public String payNotifyUrl(HttpServletRequest request) {
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
			String out_trade_no= map.get("out_trade_no");
			String total_fee=map.get("total_fee");
			String transaction_id=map.get("transaction_id");


			System.out.println("回调:"+map);

			if (map.get("return_code") != null && map.get("return_code").equals("SUCCESS")) {
				if (getSign(map).equals(map.get("sign"))) {
					OrderDO o=orderDao.get0(out_trade_no);
					if(o == null){
						o = orderDao.get1(out_trade_no);
					}
					if(o!=null){
						//Integer totalAcount = Integer.valueOf(String.valueOf(map.get("total_fee")));// 订单总金额，单位为分
						if((int)(o.getPayAmount()*100) == Integer.parseInt(total_fee)){
							String payState="0";
							String paydesc="";
							if (map.get("result_code") != null && map.get("result_code").equals("SUCCESS")) {
								payState = "1";
								paydesc="支付成功";
							}else{
								payState = "2";
								paydesc=map.get("return_msg");
							}
//							producer.sendMessage(attach+"&"+o.getFacade().getId());
							OrderDO orderDO = new OrderDO();
							orderDO.setId(o.getId());
							orderDO.setStatus(payState);
							orderDO.setPayTradeNo(transaction_id);
							orderDO.setPayDesc(paydesc);
							orderDO.setPayCreateTime(new Date());
							orderDao.update(orderDO);

							return returnXML("SUCCESS", "OK");
						}else{
							System.out.println("订单金额错误，订单号：" + out_trade_no + ",订单金额：" + total_fee);
							return returnXML("FAIL", "订单金额错误");
						}
					}else{
						System.out.println("订单不存在，订单号：" + out_trade_no);
						return returnXML("FAIL", "订单不存在");
					}
				}else{
					System.out.println("微信签名失败！");
					return returnXML("FAIL", "签名失败");
				}
			}else{
				System.out.println("微信支付失败！");
				return returnXML("FAIL", "微信支付失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("处理业务失败！");
			return returnXML("FAIL", "处理失败");
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
			HttpPost httpost = new HttpPost("https://api.mch.weixin.qq.com/sandboxnew/pay/refund");
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
	 * 解密用户敏感数据获取用户信息
	 * @param sessionKey 数据进行加密签名的密钥
	 * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
	 * @param iv 加密算法的初始向量
	 */
	@Override
	public Map<String, Object> getPhonenumber(String encryptedData,String sessionKey,String iv) {

		try {// 被加密的数据
			byte[] dataByte = Base64Util.decode(encryptedData);
			// 加密秘钥
			byte[] keyByte = Base64Util.decode(sessionKey);
			// 偏移量
			byte[] ivByte = Base64Util.decode(iv);

			// 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			// 初始化
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, "UTF-8");
				return R.hashMapOk("解密成功",JSONObject.fromObject(result));
			}else{
				return R.hashMapError("解密失败");
			}
		}catch (Exception e) {
			e.printStackTrace();
			return R.hashMapError("解密失败");
		}
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

	public String getSign(Map<String, String> map) {
		SortedMap<String, String> signParams = new TreeMap<String, String>();
		for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
			signParams.put(stringStringEntry.getKey(), stringStringEntry.getValue());
		}
		signParams.remove("sign");
		String sign = RSASignatureUtil.createSign(signParams,shaxiangKey);
//		String sign = RSASignatureUtil.createSign(signParams, Constant.PARAM_MAP.get("key"));
		return sign;
	}


	private String returnXML(String return_code,String msg) {

		return "<xml><return_code><![CDATA["

				+ return_code

				+ "]]></return_code><return_msg><![CDATA["
				+ msg
				+"]]></return_msg></xml>";
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
