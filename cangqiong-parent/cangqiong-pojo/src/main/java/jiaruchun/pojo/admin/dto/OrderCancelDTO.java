package jiaruchun.pojo.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*接受取消订单接口的对象*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelDTO {
    private String cancelReason;
    private Integer id;
}
