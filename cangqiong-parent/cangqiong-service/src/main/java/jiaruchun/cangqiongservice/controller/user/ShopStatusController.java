package jiaruchun.cangqiongservice.controller.user;

import jiaruchun.cangqiongservice.service.user.ShopStatusService;
import jiaruchun.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/shop/status")
public class ShopStatusController {

    @Autowired
    private ShopStatusService shopStatusService;

    @GetMapping
    public Result getStatus(){
        Integer status = shopStatusService.getStatus();
        return Result.success(status);
    }
}
