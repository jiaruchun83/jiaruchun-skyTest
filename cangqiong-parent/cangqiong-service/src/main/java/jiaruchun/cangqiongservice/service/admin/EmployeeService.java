package jiaruchun.cangqiongservice.service.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jiaruchun.cangqiongservice.mapper.EmployeeMapper;
import jiaruchun.pojo.admin.entity.Employee;
import jiaruchun.pojo.PageResult;
import jiaruchun.common.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public void addemp(Employee employee) {

        employee.setCreateTime(LocalDateTime.now());

        employee.setUpdateTime(LocalDateTime.now());

        employee.setPassword("123456");

        employee.setStatus(1);

        employee.setCreateUser(ThreadLocalUtil.get());
        employee.setUpdateUser(ThreadLocalUtil.get());

        employeeMapper.addEmp(employee);

    }

    public PageResult<Employee> reAll(String name, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Employee> employees = employeeMapper.reAll(name);
        Page<Employee> result = (Page<Employee>) employees;

        return new PageResult<>(result.getTotal(),result.getResult());

    }

    public void upStatus(Integer status, Long id) {
        employeeMapper.upStatus(status,id,ThreadLocalUtil.get());
    }

    public void upEmp(Employee employee) {
        employee.setUpdateUser(ThreadLocalUtil.get());
        employeeMapper.upEmp(employee);
    }
}
