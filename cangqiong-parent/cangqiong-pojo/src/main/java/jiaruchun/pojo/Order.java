package jiaruchun.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jiaruchun.pojo.user.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private Long id;
    private String number;//订单号
    private Integer status;//订单状态
    private Long userId;
    private Long addressBookId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;//下单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkoutTime;//付款时间
    private Integer payMethod;//支付方式
    private Integer payStatus;//支付状态
    private BigDecimal amount;//订单金额
    private String remark;//备注
    private String phone;
    private String address;
    private String userName;
    private String consignee;//收货人
    private String cancelReason;
    private String rejectionReason;//拒单原因
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cancelTime;//取消时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime;//预计发送时间
    private Integer deliveryStatus;//配送状态
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deliveryTime;
    private Integer packAmount;
    private Integer tablewareNumber;
    private Integer tablewareStatus;

    private List<OrderDetail> orderDetailList;
}
