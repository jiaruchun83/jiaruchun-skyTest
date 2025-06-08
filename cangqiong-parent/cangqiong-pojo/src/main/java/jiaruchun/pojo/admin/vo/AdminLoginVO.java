package jiaruchun.pojo.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginVO {
    private Long id;
    private String username;
    private String password;
    private String token;
}
