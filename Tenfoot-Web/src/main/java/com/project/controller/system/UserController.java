package com.project.controller.system;

import com.project.common.annotation.Log;
import com.project.common.config.BootdoConfig;
import com.project.domain.commom.Tree;
import com.project.domain.system.DeptDO;
import com.project.domain.system.RoleDO;
import com.project.domain.system.UserDO;
import com.project.server.system.RoleService;
import com.project.server.system.UserService;
import com.project.utils.*;
import com.qiniu.util.Auth;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jome on 2017/12/1.
 */
@RequestMapping("/sys/user")
@Controller
public class UserController {
    private String prefix="system/user"  ;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    private BootdoConfig bootdoConfig;

//    @Autowired
//    StringRedisTemplate stringRedisTemplate;


    @RequiresPermissions("sys:user:user")
    @GetMapping("")
    String user(Model model) {
        return prefix + "/user";
    }

    @GetMapping("/list")
    @ResponseBody
    PageUtils list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);
        List<UserDO> sysUserList = userService.list(query);
        if(sysUserList.size()>0){
            if(!ShiroUtils.getUser().getUsername().equals("admin")){
                for(int i=0;i<sysUserList.size();i++){
                    if(sysUserList.get(i).getUsername().equals("admin")){
                        sysUserList.remove(i);
                    }
                }
            }

        }

        int total = userService.count(query);
        PageUtils pageUtil = new PageUtils(sysUserList, total);
        return pageUtil;
    }

    @RequiresPermissions("sys:user:add")
    @Log("添加用户")
    @GetMapping("/add")
    String add(Model model) {
        List<RoleDO> roles = roleService.list();
        model.addAttribute("roles", roles);
        return prefix + "/add";
    }

    @RequiresPermissions("sys:user:edit")
    @Log("编辑用户")
    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable("id") Long id) {
        UserDO userDO = userService.get(id);
        model.addAttribute("user", userDO);
        List<RoleDO> roles = roleService.list(id);
        model.addAttribute("roles", roles);
        return prefix+"/edit";
    }

    @RequiresPermissions("sys:user:add")
    @Log("保存用户")
    @PostMapping("/save")
    @ResponseBody
    R save(UserDO user) {
        if(user.getDeptId() == null){
            return R.error(0,"请选择所属部门");
        }
        user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
        if (userService.save(user) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:user:edit")
    @Log("更新用户")
    @PostMapping("/update")
    @ResponseBody
    R update(UserDO user) throws Exception{
        if (userService.update(user) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:user:remove")
    @Log("删除用户")
    @PostMapping("/remove")
    @ResponseBody
    R remove(Long id) {
        if (userService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:user:batchRemove")
    @Log("批量删除用户")
    @PostMapping("/batchRemove")
    @ResponseBody
    R batchRemove(@RequestParam("ids[]") Long[] userIds) {
        int r = userService.batchremove(userIds);
        if (r > 0) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/exit")
    @ResponseBody
    boolean exit(@RequestParam Map<String, Object> params) {
        // 存在，不通过，false
        return !userService.exit(params);
    }

    @RequiresPermissions("sys:user:resetPwd")
    @Log("请求更改用户密码")
    @GetMapping("/resetPwd/{id}")
    String resetPwd(@PathVariable("id") Long userId, Model model) {

        UserDO userDO = new UserDO();
        userDO.setUserId(userId);
        model.addAttribute("user", userDO);
        return prefix + "/reset_pwd";
    }

    @Log("提交更改用户密码")
    @PostMapping("/resetPwd")
    @ResponseBody
    R resetPwd(UserDO user) {
        user.setPassword(MD5Utils.encrypt(userService.get(user.getUserId()).getUsername(), user.getPassword()));
        if (userService.resetPwd(user) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("/tree")
    @ResponseBody
    public Tree<DeptDO> tree() {
        Tree<DeptDO> tree = new Tree<DeptDO>();
        tree = userService.getTree();
        return tree;
    }

    @GetMapping("/treeView")
    String treeView() {
        return  prefix + "/userTree";
    }

    /**
     * 上传图片
     */
    @ResponseBody
    @PostMapping("/uploadImg")
    R uploadImg(@RequestParam("file") MultipartFile file, String flag) throws Exception{
        String MD = "default/";
        switch (flag){
            case "1":MD= Constant.HEAD_IMG;break;//头像
            case "2":MD=Constant.PRODUCT_IMG;break;//商品
            case "3":MD=Constant.PRODUCT_CATEGORY_IMG;break;//商品分类
        }
        String fileName = FileUtil.renameToUUID(file.getOriginalFilename());
        String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "bill/" +MD;
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            return R.error();
        }

        Auth auth = Auth.create(Constant.PARAM_MAP.get("accessKey"), Constant.PARAM_MAP.get("secretKey"));
        String token = auth.uploadToken(Constant.PARAM_MAP.get("pictureBucket"), "bill/"+MD+fileName);

        String json = HTTPRequestUtil.doPostFile(filePath+fileName,Constant.PARAM_MAP.get("uploadUrl"),"bill/"+MD+fileName,token);

        fileName = "bill/"+MD+fileName;

        fileName = Constant.PARAM_MAP.get("pictureDomain1")+fileName;

        Map<String, Object> map = new HashMap<>();
        map.put("userId",ShiroUtils.getUserId());
        map.put("headImg",fileName);
        if(flag.equals("1")){
            if (userService.uploadHeadImg(map) > 0) {
                ShiroUtils.getUser().setHeadImg(fileName);
                return R.ok().put("fileName",fileName);
            }
        }

        return R.ok().put("fileName",fileName);

    }

//    @ResponseBody
//    @PostMapping("/uploadImg")
//    R uploadImg(@RequestParam("file") MultipartFile file, String flag) {
//        String MD = "default/";
//        switch (flag){
//            case "1":MD= Constant.HEAD_IMG;break;//头像
//            case "2":MD=Constant.PRODUCT_IMG;break;//商品
//            case "3":MD=Constant.PRODUCT_CATEGORY_IMG;break;//商品分类
//        }
//        String fileName = FileUtil.renameToUUID(file.getOriginalFilename());
//        String filePath = bootdoConfig.getUploadPath()+MD;
//        try {
//            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
//        } catch (Exception e) {
//            return R.error();
//        }
//        fileName = Constant.PROJECT_NAME + "/files/"+MD+fileName;
//        Map<String, Object> map = new HashMap<>();
//        map.put("userId",ShiroUtils.getUserId());
//        map.put("headImg",fileName);
//        if(flag.equals("1")){
//            if (userService.uploadHeadImg(map) > 0) {
//                ShiroUtils.getUser().setHeadImg(fileName);
//                return R.ok().put("fileName",fileName);
//            }
//        }
//
//        return R.ok().put("fileName",fileName);
//
//    }

    /**
     * 门店信息
     */
    @RequiresPermissions("sys:user:userInfo")
    @GetMapping("/userInfo")
    String userInfo(Model model) {
        UserDO user = userService.get(ShiroUtils.getUserId());
        model.addAttribute("user", user);

        return prefix + "/userInfo";
    }


    /**
     * 解锁/锁定
     * @param id
     * @return
     */
    @RequiresPermissions("sys:user:disable")
    @Log("删除用户")
    @PostMapping("/userDisable")
    @ResponseBody
    R userDisable(Long id) {
//        UserDO user = userService.get(id);

//        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
//        if ("LOCK".equals(opsForValue.get(Constant.SHIRO_IS_LOCK+user.getUsername()))){
//            //解锁
//            opsForValue.set(Constant.SHIRO_LOGIN_COUNT+user.getUsername(), "0");
//            opsForValue.set(Constant.SHIRO_IS_LOCK+user.getUsername(), "");
//        }else{
//            //加锁
//            opsForValue.set(Constant.SHIRO_LOGIN_COUNT+user.getUsername(), "5");
//            opsForValue.set(Constant.SHIRO_IS_LOCK+user.getUsername(), "LOCK");
//        }
//
//        new UserRealm().removeUserAuthorizationInfoCache(user.getUsername());


        return R.ok();
    }

}
