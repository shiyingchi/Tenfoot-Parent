package com.project.server.common.sms;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by jome on 2018/1/5.
 */
public interface SmsService {

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    Map<String,Object> sendVerifyCode(String phone,HttpServletRequest request);

    /**
     * 校验验证码
     *
     * @return
     * @throws Exception
     */
    Map<String, Object> smscheck(String phone,String code,String openId,Long userId);
}
