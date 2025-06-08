package jiaruchun.common.properties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "jwt.config")
@Component
public class JwtProperties {
    //jwt:
    //设置jwt签名加密时使用的秘钥
//    public static String admin_secret_key = "this_is_a_secure_key_with_at_least_32_bytes_12345678";
   // 设置jwt过期时间
//   public static Long admin_ttl = 7200000L;
     //设置前端传递过来的令牌名称
//     public static String admin_token_name = "token";
    private String secret_key;//两端公用


    private Long admin_ttl;
    private String admin_token_name;


    /*public static final String EMP_ID = "empId";
    public static final String USER_ID = "userId";
    public static final String PHONE = "phone";
    public static final String USERNAME = "username";
    public static final String NAME = "name";*/
    private String  EMP_ID;
    private String  USER_ID;
    private String  PHONE;
    private String  USERNAME;
    private String  NAME;

    private String user_token_name;
    private Long user_ttl;

}
