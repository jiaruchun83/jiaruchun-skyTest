package jiaruchun.pojo.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*返回各订单状态的数量*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {
    private Integer confirmed;
    private Integer deliveryInProgress;
    private Integer toBeConfirmed;
}
