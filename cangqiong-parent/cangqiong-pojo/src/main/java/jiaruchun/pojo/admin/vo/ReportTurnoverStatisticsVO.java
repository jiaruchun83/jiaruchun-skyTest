package jiaruchun.pojo.admin.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*营业额统计接口*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportTurnoverStatisticsVO {

    private String dateList;//日期列表,用逗号分隔
    private String turnoverList;//营业额列表,用逗号分隔
}
