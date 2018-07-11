package com.project.controller.shop;

import com.project.server.product.ProductService;
import com.project.server.system.UserService;
import com.project.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by jome on 2017/12/1.
 */
@RequestMapping("/small/shop")
@Controller
public class ShopController {
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @GetMapping("/list")
    @ResponseBody
    Map<String, Object> list(String lat,String lng) {
        Map<String, Object> shopList = userService.smallList(Double.parseDouble(lat),Double.parseDouble(lng));
        return R.hashMap(shopList);
    }

    /**
     * 获取店铺商品分类列表
     */
    @GetMapping("/findShopProduct")
    @ResponseBody
    Map<String, Object> findShopProduct(@RequestParam("userId") Long userId){
        return R.hashMap(productService.findShopProduct(userId));
    }


    /**
     * 根据商品id获取商品属性
     */
    @GetMapping("/findProductSpec")
    @ResponseBody
    Map<String, Object> findProductSpec(@RequestParam("productId") String productId){
        return R.hashMap(productService.findProductSpec(productId));
    }

}
