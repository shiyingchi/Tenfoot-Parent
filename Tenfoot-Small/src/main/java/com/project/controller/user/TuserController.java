package com.project.controller.user;

import com.project.domain.user.TuserDO;
import com.project.server.system.ParamService;
import com.project.server.user.TuserService;
import com.project.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 会员操作
 * Created by jome on 2018/1/5.
 */
@Controller
@RequestMapping("/small/user")
public class TuserController {

    @Autowired
    TuserService tuserService;

    @Autowired
    ParamService paramService;

    /**
     * 授权登陆
     * @param code
     * @return
     */
    @ResponseBody
    @GetMapping(value="/weiXinAuthorization")
    Map<String,Object> weiXinAuthorization(String code){
        return tuserService.weixinLong(code);
    }

    /**
     * 保存/修改用户信息
     */
    @ResponseBody
    @PostMapping("/userInformation")
    Map<String,Object> userInformation(TuserDO tuserDO){
        return tuserService.userInformation(tuserDO);
    }



    /**
     * 初始化参数
     */
    @ResponseBody
    @GetMapping("/initParam")
    Map<String,Object> initParam(){
        paramService.initParam();
        return R.hashMapOk(" 成功",null);
    }


    @GetMapping("/test")
    String test() {
        return "/test";
    }


}
