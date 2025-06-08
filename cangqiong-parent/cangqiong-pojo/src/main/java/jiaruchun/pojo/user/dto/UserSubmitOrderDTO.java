package jiaruchun.pojo.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSubmitOrderDTO implements Serializable {

  private Integer addressBookId;
  private BigDecimal amount;
  private Integer deliveryStatus;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime estimatedDeliveryTime;
  private Integer packAmount;
  private Integer payMethod;
  private String remark;
  private Integer tablewareNumber;
  private Integer tablewareStatus;
}
