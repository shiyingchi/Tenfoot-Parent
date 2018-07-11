import com.alibaba.fastjson.JSON;
import com.project.utils.DateTool;
import com.project.utils.HTTPRequestUtil;
import com.project.utils.SmsSendRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by jome on 2018/1/20.
 */
public class test {


    public static void main1(String[] args) throws Exception{
//
        String token = fetchBaseAccessToken("wxa40f515221f5d321","44b55679835a1a6ae72613d875a119d8");
        String url = "https://api.weixin.qq.com/card/mkt/activity/create?access_token="+token;
        JSONObject info = new JSONObject();
        JSONObject basicInfo = new JSONObject();
        JSONArray cardInfoList = new JSONArray();
        JSONObject customInfo = new JSONObject();
        //活动封面的背景颜色，可参考：选取卡券背景颜色
        basicInfo.put("activity_bg_color","Color010");
        //用户点击链接后可静默添加到列表的小程序appid；
        basicInfo.put("activity_tinyappid","wx0fc1263ca33eca59");
        //活动开始时间，精确到秒（unix时间戳）
        basicInfo.put("begin_time", DateTool.getNowDate().getTime());
        //活动结束时间，精确到秒（unix时间戳）
        basicInfo.put("end_time",DateTool.addDays(new Date(),10).getTime());
        //单个礼包社交立减金数量（3-15个）
        basicInfo.put("gift_num","15");
        //每个用户活动期间最大领取次数,最大为50，不填默认为1
        basicInfo.put("max_partic_times_act","10");
        //每个用户活动期间单日最大领取次数,最大为50，不填默认为1
        basicInfo.put("max_partic_times_one_day","10");
        //支付商户号
        basicInfo.put("mch_code","1482130082");

        JSONObject object = new JSONObject();
        //卡券ID
        object.put("card_id","p52R-1MD240IEOS9eAdsK02J4ePQ");
        //最少支付金额，单位是分
        object.put("min_amt","5");
        //奖品指定的会员卡appid。如用户标签有选择商户会员，则需要填写会员卡appid，该appid需要跟所有发放商户号有绑定关系。
//        object.put("membership_appid","wx0fc1263ca33eca59");
        //可以指定为是否小程序新用户
        object.put("new_tinyapp_user",true);
        //可以指定为所有用户
//        object.put("total_user",true);
        cardInfoList.add(object);

        customInfo.put("type","AFTER_PAY_PACKAGE");


        info.put("basic_info",basicInfo);
        info.put("card_info_list",cardInfoList);
        info.put("custom_info",customInfo);


        object = new JSONObject();
        object.put("info",info);

        String json = HTTPRequestUtil.doPost(object.toString(),url);
        System.out.println(json);






//
//        String appid = "wxa40f515221f5d321";
//        String secret = "44b55679835a1a6ae72613d875a119d8";
//        String token = fetchBaseAccessToken(appid, secret);
//        if(StringUtils.isEmpty(token)){
//            System.out.println("token获取失败");
//            return ;
//        }
//
//        //上传图片接口
//        String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=" + token;
//        System.out.println("token:"+token);
//        String imgUrl = "";
//
//        try {
//            String json = "";//HTTPRequestUtil.doPostFile("/Users/jome/Desktop/logo.png", url);
////            System.out.println("json:"+json);
////            JSONObject o = JSONObject.fromObject(json);
//            imgUrl = "http://mmbiz.qpic.cn/mmbiz_png/LCCicc6VveVrZpiafhJ40NcxojTZj3NZENHI5Cf3hBPTj0FealSaLz1xMdThemL3ZXyfxULkra1fwPbYaQ8ZEOMA/0";
////            imgUrl = o.getString("url");
//
//            JSONObject card = new JSONObject();
//            JSONObject object = new JSONObject();
//            JSONObject cash = new JSONObject();
//
//            JSONObject baseInfo = new JSONObject();
//            JSONObject advancedInfo = new JSONObject();
//            JSONObject payInfo = new JSONObject();
//            JSONObject swipeCard = new JSONObject();
//            String[] useMidList = new String[]{"485215537"};//门店id
//
//            swipeCard.put("use_mid_list",useMidList);
//            swipeCard.put("create_mid","485215537");//门店id
//            swipeCard.put("is_swipe_card",true);
//            payInfo.put("swipe_card",swipeCard);
//
//            baseInfo.put("logo_url",imgUrl);
////            baseInfo.put("pay_info",payInfo);
//            baseInfo.put("brand_name","微信餐厅");
//            baseInfo.put("code_type","CODE_TYPE_NONE");
//            baseInfo.put("title","1111");
//            baseInfo.put("color","Color010");
//            baseInfo.put("notice","请出示二维码");
//            baseInfo.put("service_phone","020-88888888");
//            baseInfo.put("description","不可与其他优惠同享\n" +
//                    "如需团购券发票，请在消费时向商户 提出");
//            JSONObject dateInfo = new JSONObject();
//            dateInfo.put("type","DATE_TYPE_FIX_TIME_RANGE");
//            dateInfo.put("fixed_term",15);
//            dateInfo.put("fixed_begin_term",0);
//            dateInfo.put("begin_timestamp", 1516377600);
////            dateInfo.put("begin_timestamp", DateTool.getNDayOfDate(new Date(),-2).getTime());
//            dateInfo.put("end_timestamp", DateTool.addDays(new Date(),10).getTime());
//
//            baseInfo.put("date_info",dateInfo);
//            baseInfo.put("can_share",false);
//            baseInfo.put("center_title","立即使用");
//            baseInfo.put("center_app_brand_user_name","wx0fc1263ca33eca59@app");
//            baseInfo.put("center_app_brand_pass","pages/index/index");
//            baseInfo.put("can_give_friend",false);
//            JSONObject sku = new JSONObject();
//            sku.put("quantity",500000);
//            baseInfo.put("sku",sku);
//            baseInfo.put("get_limit",30);
//            baseInfo.put("custom_url_name","立即使用");
//            baseInfo.put("custom_url","http://www.qq.com");
//            baseInfo.put("custom_url_sub_title","6个汉字tips");
//            baseInfo.put("promotion_url_name","更多优惠");
//            baseInfo.put("promotion_url","http://www.qq.com");
//
//            JSONObject useCondition = new JSONObject();
//            useCondition.put("accept_category","鞋类");
//            useCondition.put("reject_category","阿迪达斯");
//            useCondition.put("can_use_with_other_discount",true);
//            //代金券专用，表示起用金额（单位为分）,如果无起用门槛则填0。
//            useCondition.put("least_cost",500.00);
//
//            advancedInfo.put("use_condition",useCondition);
//
//            JSONObject abstractObject = new JSONObject();
//            abstractObject.put("abstract","微信餐厅推出多种新季菜品，期待您的光临");
//            abstractObject.put("icon_url_list",new String[]{"http://www.qq.com"});
//            advancedInfo.put("abstract",abstractObject);
//
//            JSONArray textImageList = new JSONArray();
//            JSONObject obj = new JSONObject();
//            obj.put("image_url","");
//            obj.put("text","此菜品精选食材，以独特的烹饪方法，最大程度地刺激食 客的味蕾");
//            textImageList.add(obj);
//
//            obj = new JSONObject();
//            obj.put("image_url","");
//            obj.put("text","此菜品迎合大众口味，老少皆宜，营养均衡");
//            textImageList.add(obj);
//
//            advancedInfo.put("text_image_list",textImageList);
//
//            JSONArray timeLimit = new JSONArray();
//            JSONObject timeLimitObject = new JSONObject();
//            timeLimitObject.put("type","MONDAY");
//            timeLimitObject.put("begin_hour",0);
//            timeLimitObject.put("end_hour",10);
//            timeLimitObject.put("begin_minute",10);
//            timeLimitObject.put("end_minute",59);
//            timeLimit.add(timeLimitObject);
//            advancedInfo.put("time_limit",timeLimitObject);
//            advancedInfo.put("business_service",new String[]{"BIZ_SERVICE_FREE_WIFI","BIZ_SERVICE_WITH_PET","BIZ_SERVICE_FREE_PARK","BIZ_SERVICE_DELIVER"});
//
//
//            cash.put("base_info",baseInfo);
////            cash.put("advanced_info",baseInfo);
//            cash.put("advanced_info",new JSONObject());
//            //代金券专用，表示减免金额。（单位为分）
//            cash.put("reduce_cost",400);
//            card.put("card_type","CASH");
//            card.put("cash",cash);
//            object.put("card",card);
//
//
//
//            json = HTTPRequestUtil.doPost(object.toString(),"https://api.weixin.qq.com/card/mkt/activity/create?access_token="+token);
//
//            JSONObject o = JSONObject.fromObject(json);
//            System.out.println(o);
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        String token = fetchBaseAccessToken("wxa40f515221f5d321","44b55679835a1a6ae72613d875a119d8");
//        String url = "http://api.weixin.qq.com/cgi-bin/poi/getpoi?access_token="+token;
//
//        JSONObject object = new JSONObject();
//        object.put("poi_id",485215537);
//        String json = HTTPRequestUtil.doPost(object.toString(),url);
//        System.out.println(json);



    }


    /**
     * Description:获得普通access_token
     *
     * @param appid
     * @param appsecret
     * @return
     * @author: terry
     * @date: 2017年2月23日
     * @time: 下午5:30:25
     */
    @Cacheable(value = "ehcache_time_seven_thousandr", key = "'weixin_base_access_token'")
    public static String fetchBaseAccessToken(String appid, String appsecret) {
        try {
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
            String requestUrl = url.replace("APPID", appid).replace("APPSECRET",
                    appsecret);
            String json = HTTPRequestUtil.doGet(requestUrl);
            JSONObject jsonObject = JSONObject.fromObject(json);

            System.out.println(jsonObject);
            String token = jsonObject.getString("access_token");

            return token;
        } catch (Exception e) {
            // 获取token失败
            e.printStackTrace();
            return "";
        }
    }


    public static final String charset = "utf-8";
    // 用户平台API账号(非登录账号,示例:N1234567)
    public static String account = "N164041_N2702765";
    // 用户平台API密码(非登录密码)
    public static String pswd = "UuSp4Hc91s34b9";

    public static void main(String[] args) throws UnsupportedEncodingException {

        //请求地址请登录253云通讯自助通平台查看或者询问您的商务负责人获取
        String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";
        // 短信内容
        String msg = "【十英尺】您好,您的验证码是987654";

        //验证码: 【十英尺】您好,您的验证码是987654
        //外卖:  【十英尺】尊敬的李世明，您的外卖下单成功，正在努力跑向你。门店电话：17150883278
        //预订:  【十英尺】尊敬的李世明，您的预订下单成功，好吃的在等着你。门店电话：17150883278
        //早餐:  【十英尺】尊敬的李世明，您的早餐预订成功，请记得按时取餐。门店电话：17150883278

        msg="【十英尺】尊敬的李世明，您的外卖下单成功，正在努力跑向你。门店电话：17150883278";
        msg="【十英尺】尊敬的李世明，您的预订下单成功，好吃的在等着你。门店电话：17150883278";
        //msg="【十英尺】尊敬的李世明，您的早餐预订成功，请记得按时取餐。门店电话：17150883278";

        //手机号码
        String phone = "13539856523";
        //状态报告
        String report= "true";

        //外卖：尊敬的XXX，我是十英尺咖啡师，温馨提示：您的外卖已经下单成功，正在努力跑向你。门店电话：17150883278

        //预约：尊敬的XXX，我是十英尺咖啡师，您的预约订单已经确认成功，请记得在预约时间（xx：xx）到店取餐哦。门店电话：17150883278

        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phone,report);

        String requestJson = JSON.toJSONString(smsSingleRequest);

        System.out.println("before request string is: " + requestJson);

        String response = HTTPRequestUtil.doPost( requestJson,smsSingleRequestServerUrl);

        System.out.println("response after request result is :" + response);

//		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
//
//		System.out.println("response  toString is :" + smsSingleResponse);


    }
}
