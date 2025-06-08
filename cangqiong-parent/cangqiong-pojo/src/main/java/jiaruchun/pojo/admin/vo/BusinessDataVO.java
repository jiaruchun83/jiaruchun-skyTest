package jiaruchun.pojo.admin.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*查询当日运营数据接口的返回对象*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDataVO {
    private Integer newUsers;
    private BigDecimal orderCompletionRate;//订单完成lue
    private Double turnover;//营业额
    private Double unitPrice;//平均客单价
    private Integer validOrderCount;//有效订单数
}
