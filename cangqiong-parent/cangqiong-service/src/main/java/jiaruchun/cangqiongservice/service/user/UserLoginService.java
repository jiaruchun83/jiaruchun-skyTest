package jiaruchun.cangqiongservice.service.user;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jiaruchun.cangqiongservice.config.JwtConfiguration;
import jiaruchun.cangqiongservice.config.WeChatConfiguration;
import jiaruchun.cangqiongservice.mapper.UserMapper;
import jiaruchun.common.exce.LoginErrorException;
import jiaruchun.common.utils.HttpClientUtil;
import jiaruchun.common.utils.JwtUtil;
import jiaruchun.pojo.user.vo.UserLoginVO;
import jiaruchun.pojo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserLoginService {

    @Autowired
    private WeChatConfiguration weChatConfiguration;

    @Autowired
    private JwtConfiguration jwtConfiguration;

    @Autowired
    private UserMapper userMapper;

    public UserLoginVO login(String code) {
        //向微信发授权码
        HashMap<String, String> clams = new HashMap<>();
        clams.put("appid", weChatConfiguration.getAppid());
        clams.put("secret", weChatConfiguration.getSecret());
        clams.put("js_code", code);
        clams.put("grant_type", weChatConfiguration.getGrantType());
        String s = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/jscode2session", clams);
        //拿到openid，查数据库，没有自动注册
        JSONObject jsonObject = JSON.parseObject(s);
        String openid = jsonObject.getString("openid");
        if (openid == null) {
            throw new LoginErrorException("登录错误");
        }
        //返回登录loginInfo

        User user = userMapper.ByOpenId(openid);
        HashMap<String, Object> claim = new HashMap<>();
        if (user == null) {
            //注册
            User user1 = new User();
            user1.setOpenid(openid);
            userMapper.addUser(user1);//
            claim.put(jwtConfiguration.getUSER_ID(),user1.getId());
            String token = JwtUtil.creat4eJWT(jwtConfiguration.getSecret_key(), jwtConfiguration.getUser_ttl(), claim);
            return new UserLoginVO(user1.getId(),openid,token);
        } else {
            claim.put(jwtConfiguration.getUSER_ID(),user.getId());
            String token = JwtUtil.creat4eJWT(jwtConfiguration.getSecret_key(), jwtConfiguration.getUser_ttl(), claim);
            return new UserLoginVO(user.getId(),openid,token);
        }
    }
}