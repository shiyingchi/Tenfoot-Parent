package com.project.server.common.sms.impl;

import com.project.dao.user.TuserDao;
import com.project.domain.user.TuserDO;
import com.project.server.common.sms.SmsService;
import com.project.utils.*;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jome on 2018/1/5.
 */
@Service
public class SmsServiceImpl implements SmsService {

//    @Autowired
//    private RedisService redisService;

    @Autowired
    private TuserDao tuserDao;

    @Autowired
    private EhCacheCacheManager cacheManager;



    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    @Override
    public Map<String, Object> sendVerifyCode(String phone,HttpServletRequest request) {
        Map<String,Object> map = new HashMap<String,Object>();
        if(StringUtils.isEmpty(phone)){
            map = R.hashMapError("手机号不能为空");
        }else{
            // 验证码规则：6位随机数
            int code = (int) ((Math.random() * 9 + 1) * 100000);
            Cache ehCount = (Cache) cacheManager.getCache("sendVerifyCount").getNativeCache();
            int count = ehCount.get(IPUtils.getIpAddr(request)) == null ? 0 : Integer.valueOf(ehCount.get(IPUtils.getIpAddr(request)).getObjectValue().toString());

            String msg = Constant.PARAM_MAP.get("sms_msg_title")+code+Constant.PARAM_MAP.get("sms_msg_content");

            try {
                if(count < Integer.valueOf(Constant.PARAM_MAP.get("sms_count"))){
                    SmsSendRequest smsSingleRequest = new SmsSendRequest(Constant.PARAM_MAP.get("sms_key"), Constant.PARAM_MAP.get("sms_pwd"), msg, phone,"true");
                    String response = HTTPRequestUtil.doPost(JSONObject.fromObject(smsSingleRequest).toString(),Constant.PARAM_MAP.get("sms_url"));

                    JSONObject jsonObj = JSONObject.fromObject(response);

                    if (jsonObj != null) {
                        String errorCode = jsonObj.getString("code");
                        if ("0".equals(errorCode)) {

                            // 修改后清理缓存
                            Cache ehcache = (Cache) cacheManager.getCache("sendVerifyCode").getNativeCache();

                            ehcache.put(new Element(phone, code));
                            ehCount.put(new Element(IPUtils.getIpAddr(request), ++count));

                            map = R.hashMapOk("短信验证码发送成功",null);

                        } else {
                            map = R.hashMapError("短信验证码发送失败");
                        }
                    }else{
                        map = R.hashMapError("短信验证码发送失败");
                    }
                }else{
                    map = R.hashMapError("短信验证码发送失败,今日已超过发送条数");
                }


            }catch (Exception e){
                e.printStackTrace();
                map = R.hashMapError("短信验证码发送失败");
            }
        }

        return map;
    }


    /**
     * 短信验证
     * @param phone
     * @param code
     * @param openId
     * @return
     */
    @Override
    public Map<String, Object> smscheck(String phone, String code, String openId,Long userId) {
        Map<String,Object> map = new HashMap<>(16);
        if(StringUtils.isEmpty(phone)){
            map = R.hashMapError("手机号不能为空");
        }else if(StringUtils.isEmpty(code)){
            map = R.hashMapError("验证码不能为空");
        }else if(StringUtils.isEmpty(openId)){
            map = R.hashMapError("openId不能为空");
        }else{
            Cache ehcache = (Cache) cacheManager.getCache("sendVerifyCode").getNativeCache();

            if(ehcache.get(phone).getObjectValue().toString().equals(code)){
                ehcache.remove(phone);

                TuserDO tuserDO = new TuserDO();
                tuserDO.setPhone(phone);
                tuserDO.setOpenId(openId);
                tuserDO.setUserId(userId);
                tuserDao.update(tuserDO);
                map = R.hashMapOk("短信验证成功",null);
            }else{
                map = R.hashMapError("短信验证失败");
            }
        }

        return map;
    }

}
