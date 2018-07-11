package com.project.utils;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * HTTP请求工具类
 *
 * @author
 */
public class HTTPRequestUtil {

    public static void main(String[] args) {
        try {
            String json = HTTPRequestUtil.doGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx83e23b965c17dd4b&secret=8fb4c35d9c0d4b6f40d7fc971d8f3e5a&code=021mEaZ31Tqj702XpnY31xi5Z31mEaZZ&grant_type=authorization_code");
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String doGet(String url) throws Exception {
        URL localURL = new URL(url);
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

        // httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        // httpURLConnection.setRequestProperty("Content-Type",
        // "application/x-www-form-urlencoded");

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        if (httpURLConnection.getResponseCode() >= 300) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
        }

        try {
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");//设置编码，解决乱码
            reader = new BufferedReader(inputStreamReader);

            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }

        return resultBuffer.toString();
    }

    /**
     * @param xmlInfo json格式数据
     * @param URL
     * @return
     */

    public static String doPost(String xmlInfo, String URL) {
        System.out.println("发起的数据:" + xmlInfo);
        byte[] xmlData = xmlInfo.getBytes();
        InputStream instr = null;
        java.io.ByteArrayOutputStream out = null;
        try {
            URL url = new URL(URL);
            URLConnection urlCon = url.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            urlCon.setRequestProperty("Content-Type", "text/xml");
            urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
            System.out.println(String.valueOf(xmlData.length));
            DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
            printout.write(xmlData);
            printout.flush();
            printout.close();
            instr = urlCon.getInputStream();
            byte[] bis = IOUtils.toByteArray(instr);
            String ResponseString = new String(bis, "UTF-8");
            if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
                System.out.println("返回空");
            }
            System.out.println("返回数据为:" + ResponseString);
            return ResponseString;

        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        } finally {
            try {
                instr.close();

            } catch (Exception ex) {
                return "0";
            }
        }
    }


    /**
     * @param filePath 要上传的文件的路径
     * @param url      请求地址
     * @return
     * @throws Exception
     */
    public static String doPostFile(String filePath, String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String json = "";
        try {
            //把一个普通参数和文件上传给下面这个地址    是一个servlet
            HttpPost httpPost = new HttpPost(url);
            //把文件转换成流对象FileBody
            FileBody bin = new FileBody(new File(filePath));

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("uploadfile", bin)//相当于<input type="file" name="media"/>
                    .build();


            httpPost.setEntity(reqEntity);

            System.out.println("发起请求的页面地址 " + httpPost.getRequestLine());
            //发起请求   并返回请求的响应
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                System.out.println("----------------------------------------");
                //打印响应状态
                System.out.println(response.getStatusLine());
                //获取响应对象
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    //打印响应长度
                    System.out.println("Response content length: " + resEntity.getContentLength());
                    //打印响应内容
//                    System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
                    String json1 =  EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
                    json = json1;
                }
                //销毁
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return json;
    }

}
