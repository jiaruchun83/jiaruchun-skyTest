package jiaruchun.cangqiongservice.controller.admin;

import jiaruchun.cangqiongservice.service.admin.AdminLoginService;
import jiaruchun.pojo.admin.vo.AdminLoginVO;
import jiaruchun.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/employee/login")
@CrossOrigin(origins = "*") // 允许所有域名跨域访问
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @PostMapping
    public Result login(@RequestBody AdminLoginVO adminLoginVO)  {
            log.info("收到登录请求，请求参数: {}", adminLoginVO);
            AdminLoginVO login = adminLoginService.login(adminLoginVO);
            log.info("数据库查询结果: {}", login);
        return Result.success(login);
    }


}
