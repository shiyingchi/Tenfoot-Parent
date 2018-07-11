package com.project.common.listenner;

import com.project.server.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by jome on 2018/1/16.
 */
@Component
public class Scheduler {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderService orderService;

    @Scheduled(cron="0 0/5 * * * ?") //每5分钟执行一次
    public void statusCheck() {
        logger.info("每5分钟执行一次。处理订单");
        orderService.updateOrder();
    }

//    @Scheduled(fixedRate=20000)
//    public void testTasks() {
//        logger.info("每20秒执行一次。开始……");
//        logger.info("每20秒执行一次。结束。");
//    }
}
