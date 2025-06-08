package jiaruchun.cangqiongservice.controller.admin;

import jiaruchun.cangqiongservice.service.admin.EmployeeService;
import jiaruchun.pojo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jiaruchun.pojo.admin.entity.Employee;
import jiaruchun.pojo.Result;

@Slf4j
@RestController
@RequestMapping("/admin/employee")
@CrossOrigin(origins = "*") // 允许所有域名跨域访问
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Result addEmp(@RequestBody Employee employee){
        employeeService.addemp(employee);
        log.info("新增员工，员工信息：{}",employee);
        return Result.success();
    }

    @GetMapping("/page")
    public Result reAll(@RequestParam(value = "name",required = false) String name,
                        @RequestParam(value = "page") Integer page,
                        @RequestParam(value = "pageSize") Integer pageSize){
        log.info("分页查询，参数：{},{},{}",name,page,pageSize);
        PageResult<Employee> employeePageResult = employeeService.reAll(name, page, pageSize);
        log.info("查询结果：{}",employeePageResult);
        return Result.success(employeePageResult);
    }

    @PostMapping("/status/{status}")
    public Result upStatus(@PathVariable("status") Integer status, @RequestParam("id") Long id){
        employeeService.upStatus(status,id);
        return Result.success();
    }

    @PutMapping
    public Result upEmp(@RequestBody Employee employee){
        employeeService.upEmp(employee);
        return Result.success();
    }

    @PostMapping("/logout")
    public Result logout(){
        return Result.success();
    }

}
