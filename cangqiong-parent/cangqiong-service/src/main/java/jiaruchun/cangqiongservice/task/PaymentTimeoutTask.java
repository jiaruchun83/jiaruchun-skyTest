package jiaruchun.cangqiongservice.task;

import jiaruchun.cangqiongservice.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*订单超时任务,轮询，后期改mq*/
@Slf4j
@Component
@EnableScheduling
public class PaymentTimeoutTask {


    @Autowired
    private OrderMapper orderMapper;
    /*
    * 订单超时任务
    * */
    //每个一分钟执行一次
    @Scheduled(cron = "0 */1 * * * ?")
    public void timeoutTask(){
        log.info("清理支付延迟的订单");
        //更新支付延迟的订单
        orderMapper.upPaymentTimeoutOrders();
    }
}
