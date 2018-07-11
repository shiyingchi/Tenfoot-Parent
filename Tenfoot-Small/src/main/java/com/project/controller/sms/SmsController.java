package com.project.controller.sms;

import com.project.server.common.sms.SmsService;
import com.project.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by jome on 2017/12/1.
 */
@RequestMapping("/small/sms")
@Controller
public class SmsController {
    @Autowired
    SmsService smsService;


    @ResponseBody
    @PostMapping("/sentObtain")
    Map<String, Object> sentObtain(String phone,HttpServletRequest request) {
        try{
            return smsService.sendVerifyCode(phone,request);
        }catch(Exception e){
            return R.hashMapError("发送验证码失败");
        }
    }

    @ResponseBody
    @PostMapping("/smsCheck")
    Map<String, Object> smsCheck(String phone, String code,String openId,Long userId) {
        try{
            return smsService.smscheck(phone,code,openId,userId);
        }catch(Exception e){
            return R.hashMapError("短信验证码失败");
        }
    }

}
