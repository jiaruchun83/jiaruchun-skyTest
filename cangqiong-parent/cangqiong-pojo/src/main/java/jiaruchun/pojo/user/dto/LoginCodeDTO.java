package jiaruchun.pojo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCodeDTO {
    private String code;//接收微信登录授权码
}
