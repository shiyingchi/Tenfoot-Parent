package com.project.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;


public class RSASignatureUtil {

	//
	// /**
	// * 花椒充值使用测试私钥，pkcs8
	// */
	// public final static String FENG_MI_PRIVATEKEY =
	// "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAINHDvkY7UJAidJTIeGTQUG2hNi7JEGjOdUdMZObKE7CXwr"
	// + "G3Zs0pJS3nf8uEdLo3LB/lMYxeBS+meSAnJELJJtJH+H2zbgLWVfBZiR0yH4RaD"
	// + "5ejLzoGC1Y4IO/4v9MORn32uaOYrN/vcWYkplJO2hPsU2jTYdcHsT3OcD3qJElAgMBAA"
	// +
	// "ECgYB7/P8tUde0vVPubEoRzYzSzdpHAOnLfKjAmeQbL24whPBTM2RWlK/lLk9xlkeLVhgqMzWGw2"
	// +
	// "5Q2XxsDvTRIR8emJlQpKWdbMlyjRFftFBxg0WT0O7JmsrFRuh4Lm64JeHU8oX3rbSYxPj8arhRVJ9w"
	// +
	// "QftCTBQaPiiX/7ApMTQZgQJBAL/fxDU8a1W6SHHiK+bPrc8rMr41I5xDtTiO3Dws/RF17r6NC31kkr6"
	// +
	// "G7CUdXeJeaUKkMvVJkHu0k2qHn/wOFjUCQQCvJtD6hnalKlUryD364mofDfVj1RgrOtB6MRWS2kkudE"
	// +
	// "dJxOz1mIRKIMQ+YCW3xgnsH6gLnbeF1iefVunYsy0xAkA2Pez628TALHRUdYrRqj5Jhb3HlcAmaeGuR"
	// +
	// "nyuKk20hPFkPv1Tin4NjJjveRjUaLo7E64wN4a34fmWsRaiK91NAkBkXNiohT4c/DnboZ0qUmy+qK/w6"
	// +
	// "Z00wDnPmqqjAGOos8ZZlfa18BP1qfrpRwy11ku/OpB/P3t2vDTuD85ldp4xAkBPURbLPpIZJyJDQE0oM"
	// + "Ahh+8yvWQ/EbmV77F25xuItoRkSfOhUcqJT9wMGfBzCXyH+qV1cRwnRMU0FLidPD009";
	// /**
	// * 花椒充值使用测试公钥
	// */
	// public final static String FENG_MI_PUBLICKEY =
	// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDRw75GO1CQInSUyHhk0FBtoTY"
	// + "\n"
	// + "uyRBoznVHTGTmyhOwl8Kxt2bNKSUt53/LhHS6Nywf5TGMXgUvpnkgJyRCySbSR/h"
	// + "\n"
	// + "9s24C1lXwWYkdMh+EWg+Xoy86BgtWOCDv+L/TDkZ99rmjmKzf73FmJKZSTtoT7FN"
	// + "\n" + "o02HXB7E9znA96iRJQIDAQAB" + "\n";
	// /**
	// * 花椒充值使用正式私钥，pkcs8
	// */
	// public final static String FENG_MI_PRIVATEKEY =
	// "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL8lJ3XEsjo7EBI3"
	// + "Upj0HyfnNg7muJatFcnDdjc1VnaDMwQBbQ3Q8d1yc+OA4NnbCGPOJVz8FwUVhlA6"
	// + "J1V8FYioWr81xUpEM96DF4/c2+W63sFzB2IRUplD5MM9fUTsKiBNL5/g7Qa0Do1M"
	// + "h4RKmDkP4hX0KIHACGTXF4yS0BGvAgMBAAECgYBralizgNbbjGdqpTZAiCeeB/vE"
	// + "AfpHinsNCE+9lLbDINWhcZ+0PPA+jtv7RGiXZNRhKe6rQ2s4SzFQkm2aR4HdcE0P"
	// + "V4gIp1mO3wNM5jZ1cHZICU8nZWZae+I9Nr+pE/SF1JptCptlTu+HRdqRu36/hVS5"
	// + "QwmbMVGf7t00K0Z9iQJBAPzcR+spIM0jULhiG8eATRkWc44M1H/AFdOyz/AhSvvs"
	// + "Uiw5+1uuIkj84K+XdegBHY/r3H5RSypoffzCevKXQZMCQQDBhLXjcKPkI5PM7pPm"
	// + "2Iss3/D5yfB/vqAk+Qxo0Rh9rx2bKgAZqxQyiY62xNZk3ernkL++A6gWrdDl56Np"
	// + "6nD1AkEAzLtyg6X2SAIrDk9pXJu2UPTLtR0QRSt6wMCL+kqKkvViBysfKLkSS3Rs"
	// + "W9/PeHzGucvn6GLb8EdQkwHIWHk9fwJAeuSfab93pQLe8q6z6E6uG6JfcxHGFCnn"
	// + "o3MxaUMYTYCuyYHjrGlTm0B2DCV2jXnMmp/21GGYFkbtuYLf6PxEHQJBAJY48CS7"
	// + "abcp8vs0iGlCQ4/qBjc+r7Wy4OBrKHV1dsnSYngZcvbxafFxMbFtSpBRFdU+yEwy"
	// + "XVcfIGZWn2TZU/w=";
	// /**
	// * 花椒充值使用正式公钥
	// */
	// public final static String FENG_MI_PUBLICKEY =
	// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/JSd1xLI6OxASN1KY9B8n5zYO" +
	// "5riWrRXJw3Y3NVZ2gzMEAW0N0PHdcnPjgODZ2whjziVc/BcFFYZQOidVfBWIqFq/" +
	// "NcVKRDPegxeP3Nvlut7BcwdiEVKZQ+TDPX1E7CogTS+f4O0GtA6NTIeESpg5D+IV" +
	// "9CiBwAhk1xeMktARrwIDAQAB";

	// /**
	// * 花椒测试公钥
	// */
	// public final static String HUA_JIAO_PUBLICKEY =
	// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0o75bZUxDHfU11cjGZT6bJSH2"
	// + "b+/R6E1kAjZh8cw6E5vv1kQjxY/xD+LC0iFf+p5mxVevxPPWsWP/LzGnbst43I0d"
	// + "eTP6cqEogYir/P9PDA7owp4RNvZNTOEIKRTgPGJL7IoNVos1KvuK/faaidh1fyhy"
	// + "71iAby+imF5nweufaQIDAQAB";

	/**
	 * 签名算法
	 */
	public static final String SIGN_ALGORITHMS = "SHA1withRSA";

	public static final String charset = "UTF-8";

	/** 密钥 */
	private String key;

	private HttpServletRequest request;

	private HttpServletResponse response;

	public RSASignatureUtil(HttpServletRequest request, HttpServletResponse response) {
		this.response = response;
		this.request = request;
	}

	/**
	 * 获取密钥
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 设置密钥
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * RSA签名
	 * 
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            商户私钥
	 * @param encode
	 *            字符集编码
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey, String encode) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Util.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes(encode));
			byte[] signed = signature.sign();
			return Base64Util.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            商户自己的私钥
	 * @return
	 */

	public static String sign(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Util.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes());
			byte[] signed = signature.sign();
			return Base64Util.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA验签名检查
	 *
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param publicKey
	 *            分配给开发商公钥
	 * @param encode
	 *            字符集编码
	 * @return 布尔值
	 */
	public static boolean doCheck(String content, String sign, String publicKey, String encode) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64Util.decode(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update(content.getBytes(encode));

			boolean bverify = signature.verify(Base64Util.decode(sign));
			return bverify;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean doCheck(String content, String sign, String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64Util.decode(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update(content.getBytes());

			boolean bverify = signature.verify(Base64Util.decode(sign));
			return bverify;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 检查签名方法
	 */
	public static boolean checkAndUpdate(String sign, Map<String, String> returnMessage, String weixinKey) {
		Iterator<Entry<String, String>> entries = returnMessage.entrySet().iterator();
		StringBuffer sb = new StringBuffer();
		while (entries.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) entries.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			if (!key.equals("sign")) {
				sb.append(key);
				sb.append("=");
				sb.append(value);
				sb.append("&");
			}

		}
		sb.append("key=");
		sb.append(weixinKey);
		String signMD5 = DigestUtils.md5Hex(sb.toString());
		signMD5 = signMD5.toUpperCase();
		if (signMD5.equals(sign)) {
			return true;
		}
		return false;
	}

	/**
	 * 花椒f充值rsa加密方法
	 */
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
	public static final String ENCODING = "utf-8";
	public static final String X509 = "X.509";

	/**
	 * 花椒f充值rsa加密方法
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(key.getBytes(ENCODING));
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 花椒f充值rsa加密方法
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(key.getBytes(ENCODING));
		CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
		InputStream in = new ByteArrayInputStream(keyBytes);
		Certificate certificate = certificateFactory.generateCertificate(in);
		PublicKey publicKey = certificate.getPublicKey();
		return publicKey;
	}

	/**
	 * 花椒f充值rsa加密方法
	 */
	public static String signByPrivateKey(String content, String privateKey) {
		try {
			PrivateKey priKey = getPrivateKey(privateKey);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(priKey);
			signature.update(content.getBytes(ENCODING));
			byte[] signed = signature.sign();
			// return new String(Base64.encodeBase64(signed), ENCODING);
			return new String(Base64.encodeBase64URLSafe(signed), ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 花椒f充值rsa加密方法
	 */
	public static boolean verifySignByPublicKey(String content, String sign, String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decodeBase64(publicKey.getBytes(ENCODING));
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(pubKey);
			signature.update(content.getBytes(ENCODING));
			return signature.verify(Base64.decodeBase64(sign.getBytes(ENCODING)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public static String createSign(SortedMap<String, String> packageParams, String key) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		String sign = MD5Utils.MD5Encode(sb.toString(), charset).toUpperCase();
		return sign;

	}

//	public boolean isTenpaySign(Map map) {
//		if (Utils.isEmpty(map)) {
//			System.out.println("参数map为空！");
//			return false;
//		}
//		StringBuffer sb = new StringBuffer();
//		Set es = map.entrySet();
//		Iterator it = es.iterator();
//		while (it.hasNext()) {
//			Entry entry = (Entry) it.next();
//			String k = (String) entry.getKey();
//			String v = (String) entry.getValue();
//			if (!"sign".equals(k) && null != v && !"".equals(v)) {
//				sb.append(k + "=" + v + "&");
//			}
//		}
//		sb.append("key=" + getKey());
//		// 算出摘要
//		String enc = this.getCharacterEncoding(this.request, this.response);
//		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();
//		String tenpaySign = String.valueOf(map.get("sign")).toLowerCase();
//		boolean bool = tenpaySign.equals(sign);
//		return bool;
//	}

	/**
	 * 获取编码字符集
	 * 
	 * @param request
	 * @param response
	 * @return String
	 */
	public static String getCharacterEncoding(HttpServletRequest request, HttpServletResponse response) {

		if (null == request || null == response) {
			return "gbk";
		}

		String enc = request.getCharacterEncoding();
		if (null == enc || "".equals(enc)) {
			enc = response.getCharacterEncoding();
		}

		if (null == enc || "".equals(enc)) {
			enc = "gbk";
		}

		return enc;
	}

}

