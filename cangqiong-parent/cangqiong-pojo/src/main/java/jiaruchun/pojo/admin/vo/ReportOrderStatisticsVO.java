package jiaruchun.pojo.admin.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*订单统计接口*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportOrderStatisticsVO {
    private String dateList;//日期列表,用逗号分隔
    private String validOrderCountList;//有效订单数量列表,用逗号分隔
    private String orderCountList;//订单数量列表,用逗号分隔
    private Double orderCompletionRate;//订单完成率
    private Integer totalOrderCount;//总订单数量
    private Integer validOrderCount;//有效订单数量
}
