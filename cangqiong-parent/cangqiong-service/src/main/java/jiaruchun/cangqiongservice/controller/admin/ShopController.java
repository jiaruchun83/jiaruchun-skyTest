package jiaruchun.cangqiongservice.controller.admin;

import jiaruchun.cangqiongservice.service.user.ShopStatusService;
import jiaruchun.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shop")
public class ShopController {

    @Autowired
    private ShopStatusService shopStatusService;

    @GetMapping("/status")
    public Result getStatus(){
        Integer status = shopStatusService.getStatus();
        return Result.success(status);
    }

    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status){
        shopStatusService.setStatus(status);
        return Result.success();
    }

}
