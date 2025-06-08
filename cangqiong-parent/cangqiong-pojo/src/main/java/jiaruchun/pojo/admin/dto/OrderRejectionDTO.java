package jiaruchun.pojo.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*接受拒单接口的对象*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRejectionDTO {
    private Long id;
    private String rejectionReason;
}
