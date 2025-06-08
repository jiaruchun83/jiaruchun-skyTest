package jiaruchun.cangqiongservice.controller.user;

import jiaruchun.cangqiongservice.service.user.UserLoginService;
import jiaruchun.pojo.Result;
import jiaruchun.pojo.user.dto.LoginCodeDTO;
import jiaruchun.pojo.user.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user/user")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginCodeDTO loginCodeDTO){
        UserLoginVO login = userLoginService.login(loginCodeDTO.getCode());
        return Result.success(login);
    }

    @PostMapping("/logout")
    public Result logout(){
        return Result.success();
    }

}
