package jiaruchun.pojo.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
* 下单后返回对象
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitOrderVO {
    private Long id;
    private BigDecimal orderAmount;
    private String orderNumber;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;//下单时间
}
