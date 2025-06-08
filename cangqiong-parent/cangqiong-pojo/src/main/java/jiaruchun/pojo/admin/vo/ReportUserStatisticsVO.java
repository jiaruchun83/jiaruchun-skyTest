package jiaruchun.pojo.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*用户统计接口*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportUserStatisticsVO {
    private String dateList;//日期列表,用逗号分隔
    private String newUserList;//新增用户列表,用逗号分隔
    private String totalUserList;//总用户列表,用逗号分隔
}
