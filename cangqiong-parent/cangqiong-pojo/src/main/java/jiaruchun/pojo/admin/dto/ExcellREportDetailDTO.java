package jiaruchun.pojo.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcellREportDetailDTO {
    private LocalDate date;
    private BigDecimal turnover;
    private Integer validOrderCount;
    private String orderCompletionRate;
    private BigDecimal avgOrderAmount;
    private Integer newUserCount;
}
