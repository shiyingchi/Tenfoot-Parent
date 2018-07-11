package com.project.controller.system;

import com.project.common.annotation.Log;
import com.project.domain.commom.Tree;
import com.project.domain.system.MenuDO;
import com.project.domain.system.UserDO;
import com.project.server.system.MenuService;
import com.project.server.system.ParamService;
import com.project.server.system.UserService;
import com.project.utils.MD5Utils;
import com.project.utils.R;
import com.project.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * Created by jome on 2017/11/30.
 */
@Controller
public class LoginController  {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MenuService menuService;
    @Autowired
    UserService userService;
    @Autowired
    ParamService paramService;

    @GetMapping({"/",""})
    String welcome(Model model){/* return "redirect:/blog"; */ return "login"; }

    @GetMapping("/login")
    String login(){return "login";}

//    @RequestMapping(value = "/imageCode")
//    public void imagecode(HttpServletResponse response) throws Exception {
//        OutputStream os = response.getOutputStream();
//        BufferedImage img = ImageCode.getImageCode();
//        ImageIO.write(img, "JPEG", os);
//    }

    @Log("登录")
    @PostMapping("/login")
    @ResponseBody
    R ajaxLogin(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, MD5Utils.encrypt(username, password));
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.isPermitted(username);
            subject.login(token);
            return R.ok();
        } catch (AuthenticationException e) {
            e.printStackTrace();
//            if(e.getMessage().contains("Authentication failed for token submission")){
//                return R.error("账号或密码错误");
//            }else{
                return R.error(e.getMessage());
//            }

        }
    }

    public static void main(String[] args) {
        System.out.println(MD5Utils.encrypt("sycgm", "sycgm"));
//        4a48251cef3f0401c6eeab7a53c8311c
    }

    @Log("请求访问主页")
    @GetMapping("/index")
    String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(ShiroUtils.getUser());
        model.addAttribute("menus", menus);
        //因redis缓存问题bug  后期需要修改
        UserDO user = userService.get(ShiroUtils.getUserId());
        ShiroUtils.getUser().setHeadImg(user.getHeadImg());
        ShiroUtils.getUser().setName(user.getName());

        model.addAttribute("name", ShiroUtils.getUser().getName());
        model.addAttribute("headImg", ShiroUtils.getUser().getHeadImg());
        logger.info(ShiroUtils.getUser().getName());
        return "index";
    }


    @RequestMapping("/logout")
    String logout() {
        System.out.println("退出");
        ShiroUtils.logout();
//        return "redirect:/login";
        return "login";
    }

    @GetMapping("/main")
    String main() {
        return "main";
    }

    @GetMapping("/403")
    String error403() {
        return "403";
    }

    @GetMapping("/print")
    String print(){return "printfHtml";}


    /**
     * 初始化参数
     */
    @ResponseBody
    @GetMapping("/initParam")
    Map<String,Object> initParam(){
        paramService.initParam();
        return R.hashMapOk(" 成功",null);
    }

}
