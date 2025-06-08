package jiaruchun.cangqiongservice.controller.user;

import jiaruchun.cangqiongservice.service.user.ShoppingCartService;
import jiaruchun.pojo.Result;
import jiaruchun.pojo.user.entity.ShoppingCart;
import jiaruchun.pojo.user.dto.ShoppingCartDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public Result reShoppingCart(){
        List<ShoppingCart> shoppingCarts = shoppingCartService.reShoppingCart();
        return Result.success(shoppingCarts);
    }

    @PostMapping("/add")
    public Result addShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    @PostMapping("/sub")
    public Result remove(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shoppingCartService.remove(shoppingCartDTO);
        return Result.success();
    }

    @DeleteMapping("/clean")
    public Result clean(){
        shoppingCartService.clean();
        return Result.success();
    }
}
