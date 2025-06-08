package jiaruchun.cangqiongservice.controller.admin;

import jiaruchun.cangqiongservice.service.admin.AdminOrderService;
import jiaruchun.pojo.Order;
import jiaruchun.pojo.PageResult;
import jiaruchun.pojo.Result;
import jiaruchun.pojo.admin.dto.OrderCancelDTO;
import jiaruchun.pojo.admin.dto.OrderConfirmDTO;
import jiaruchun.pojo.admin.dto.OrderRejectionDTO;
import jiaruchun.pojo.admin.vo.Statistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@Slf4j
@RestController
@RequestMapping("/admin/order")
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping("/conditionSearch")
    public Result reAllOrder(@RequestParam(required = false)LocalTime beginTime,
                             @RequestParam(required = false) LocalTime endTime,
                             @RequestParam(required = false) Integer number,
                             @RequestParam(defaultValue = "1") Integer page,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String phone,
                             @RequestParam(required = false) Integer status){
        PageResult<Order> orderPageResult = adminOrderService.reAll(beginTime, endTime, number, phone, status, page, pageSize);
        return Result.success(orderPageResult);
    }

    /*各状态的订单统计*/
    @GetMapping("/statistics")
    public Result getStatistics(){
        Statistics statistics = adminOrderService.getStatistics();
        return Result.success(statistics);
    }

    @GetMapping("/details/{id}")
    public Result getDetails(@PathVariable Long id){
        Order details = adminOrderService.getDetails(id);
        return Result.success(details);
    }

    /*取消订单*/
    @PutMapping("/cancel")
    public Result cancel(@RequestBody OrderCancelDTO orderCancelDTO){
        adminOrderService.cancel(orderCancelDTO);
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    public Result complete(@PathVariable Long id){
        adminOrderService.complete(id);
        return Result.success();
    }

    @PutMapping("/rejection")
    public Result rejection(@RequestBody OrderRejectionDTO orderRejectionDTO){
        adminOrderService.rejection(orderRejectionDTO);
        return Result.success();
    }

    @PutMapping("/confirm")
    public Result confirm(@RequestBody OrderConfirmDTO orderConfirmDTO){
        adminOrderService.confirm(orderConfirmDTO);
        return Result.success();
    }

    /*派送订单*/
    @PutMapping("/delivery/{id}")
    public Result delivery(@PathVariable Long id){
        adminOrderService.delivery(id);
        return Result.success();
    }
}
