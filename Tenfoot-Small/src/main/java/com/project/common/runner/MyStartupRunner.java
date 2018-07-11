package com.project.common.runner;

import com.project.server.system.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by jome on 2018/1/5.
 * 服务启动执行
 */
@Component
public class MyStartupRunner implements CommandLineRunner {

    @Autowired
    ParamService paramService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据操作<<<<<<<<<<<<<");
        paramService.initParam();
    }
}
