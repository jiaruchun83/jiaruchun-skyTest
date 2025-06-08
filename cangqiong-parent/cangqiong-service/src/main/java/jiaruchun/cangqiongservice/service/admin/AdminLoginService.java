package jiaruchun.cangqiongservice.service.admin;


import jiaruchun.cangqiongservice.config.JwtConfiguration;
import jiaruchun.cangqiongservice.mapper.EmployeeMapper;
import jiaruchun.common.exce.PasswordErrorException;
import jiaruchun.common.exce.UserNameErrorException;

import jiaruchun.common.utils.JwtUtil;
import jiaruchun.pojo.admin.vo.AdminLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AdminLoginService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private JwtConfiguration jwtConfiguration;

    public AdminLoginVO login(AdminLoginVO adminLoginVO)  {

        String username = employeeMapper.loginUsername(adminLoginVO);
        AdminLoginVO login = employeeMapper.loginAll(adminLoginVO);

        if(username == null){
            throw new UserNameErrorException("用户名不存在");
        }else{
            if(login == null){
                throw new PasswordErrorException("密码错误，请重新输入");
            }else{
                if(employeeMapper.reStatus(login.getId()) == 0){
                    throw new UserNameErrorException("用户已被锁定");
                }else{
                    HashMap<String, Object> clams = new HashMap<>();
                    clams.put(jwtConfiguration.getEMP_ID(),login.getId());
                    clams.put(jwtConfiguration.getUSERNAME(),login.getUsername());
                    String s = JwtUtil.creat4eJWT(jwtConfiguration.getSecret_key(), jwtConfiguration.getAdmin_ttl(),clams);
                    return new AdminLoginVO(login.getId(),login.getUsername(),login.getPassword(),s);
                }
            }
        }
    }

}
