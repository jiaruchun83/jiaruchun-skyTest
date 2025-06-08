package jiaruchun.cangqiongservice.controller.user;

import jiaruchun.cangqiongservice.service.user.UserOrderService;
import jiaruchun.pojo.Order;
import jiaruchun.pojo.PageResult;
import jiaruchun.pojo.Result;
import jiaruchun.pojo.user.dto.OrderPayDTO;
import jiaruchun.pojo.user.vo.OrderPayResultVO;
import jiaruchun.pojo.user.vo.SubmitOrderVO;
import jiaruchun.pojo.user.dto.UserSubmitOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*用户订单控制*/
@Slf4j
@RestController
@RequestMapping("/user/order")
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

    /*下单*/
    @PostMapping("/submit")
    public Result submit(@RequestBody UserSubmitOrderDTO userSubmitOrderDTO){
        SubmitOrderVO submit = userOrderService.submit(userSubmitOrderDTO);
        return Result.success(submit);
    }

    /*
    * 查询订单详情
    * */
    @GetMapping("/orderDetail/{id}")
    public Result getOrderDetail(@PathVariable Long id){
        Order order = userOrderService.getOrderDetail(id);
        return Result.success(order);
    }

    /*下单支付接口*/
    @PutMapping("/payment")
    public Result payment(@RequestBody OrderPayDTO orderPayDTO) {
        log.info("订单支付：{}", orderPayDTO);
        OrderPayResultVO payment = userOrderService.payment(orderPayDTO);
        log.info("生成预支付交易单：{}", payment);
        return Result.success(payment);
    }

    @GetMapping("/historyOrders")
    public Result getAllOrder(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(required = false) Integer status){
        PageResult<Order> allOrder = userOrderService.getAllOrder(page, pageSize, status);
        return Result.success(allOrder);
    }

    /*催单*/
    @GetMapping("/reminder/{id}")
    public Result reminder(@PathVariable Long id){
        //后面再讲,如何提醒商家
        userOrderService.reminder(id);
        return Result.success();
    }

    /*再来一单*/
    @PostMapping("repetition/{id}")
    public Result repetition(@PathVariable Long id){
        return Result.success();
    }
}
