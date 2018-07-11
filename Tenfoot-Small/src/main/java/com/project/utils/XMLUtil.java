package com.project.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;


/**
 * xml工具类
 * 
 * @author miklchen
 * 
 */
public class XMLUtil {

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(String msg) throws Exception {  
        // 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>();  
  
        // 从request中取得输入流  
        InputStream inputStream = new ByteArrayInputStream(  
                msg.getBytes("UTF-8"));  
  
        // 读取输入流  
        SAXReader reader = new SAXReader();  
        Document document = reader.read(inputStream);  
        // 得到xml根元素  
        Element root = document.getRootElement();  
        // 得到根元素的所有子节点  
        List<Element> elementList = root.elements();  
  
        // 遍历所有子节点  
        for (Element e : elementList)  
        	map.put(e.getName(), e.getText()); 
  
        // 释放资源  
        inputStream.close();  
        inputStream = null;  
  
        return map;  
    }  
	@SuppressWarnings({ "rawtypes", "unused" })
	public static String BuildXML(SortedMap<String, String> packageParams, String sign) {
		// 将根节点添加到文档中...
		org.jdom.Document doc = new org.jdom.Document();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		StringBuffer xmlStr = new StringBuffer("<xml>");
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (k != "appkey") {
				xmlStr.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		xmlStr.append("<sign>" + sign + "</sign>");
		xmlStr.append("</xml>");
		return xmlStr.toString();
	}
}
