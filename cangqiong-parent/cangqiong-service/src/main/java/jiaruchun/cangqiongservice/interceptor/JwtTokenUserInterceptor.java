package jiaruchun.cangqiongservice.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jiaruchun.common.properties.JwtProperties;
import jiaruchun.common.utils.JwtUtil;
import jiaruchun.common.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getUser_token_name());

        // 检查令牌是否为空
        if (token == null || token.isEmpty()) {
            log.error("令牌为空");
            response.setStatus(401);
            return false;
        }

        //2、校验令牌
        try {
            log.info("jwt校验:{}", token);
            Map<String,Object> claims = JwtUtil.parseJWT(jwtProperties.getSecret_key(), token);
            Object object = claims.get(jwtProperties.getUSER_ID());

            Long id = null;
            if (object instanceof Integer) {
                // 将 Integer 转换为 int，再提升为 long，最后包装成 Long
                id = ((Integer) object).longValue();
            } else if (object instanceof Long) {
                id = (Long) object;
            } else if (object instanceof String) {
                try {
                    id = Long.parseLong((String) object);
                } catch (NumberFormatException e) {
                    log.error("解析 ID 时，字符串不是有效的长整数格式: {}", object, e);
                }
            } else {
                log.error("员工  不是预期的类型，实际类型: {}", object != null ? object.getClass().getName() : "null");
            }

            if (id != null) {
                ThreadLocalUtil.set(id);
                return true;//3、通过，放行
            } else {
                response.setStatus(401);
                return false;
            }
        } catch (Exception ex) {
            //4、不通过，响应401状态码
            log.error("令牌校验失败，错误信息: {}", ex.getMessage(), ex);
            response.setStatus(401);
            return false;
        }
    }
}
