package jiaruchun.cangqiongservice.service.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jiaruchun.cangqiongservice.mapper.OrderDetailMapper;
import jiaruchun.cangqiongservice.mapper.OrderMapper;
import jiaruchun.pojo.Order;
import jiaruchun.pojo.PageResult;
import jiaruchun.pojo.admin.dto.OrderCancelDTO;
import jiaruchun.pojo.admin.dto.OrderConfirmDTO;
import jiaruchun.pojo.admin.dto.OrderRejectionDTO;
import jiaruchun.pojo.admin.vo.Statistics;
import jiaruchun.pojo.user.entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
public class AdminOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    public PageResult<Order> reAll(LocalTime beginTime, LocalTime endTime, Integer number, String phone, Integer status, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Order> orders = orderMapper.reAll(beginTime, endTime, number, phone, status);
        Page<Order> orders1 = (Page<Order>) orders;
        return new PageResult<>(orders1.getTotal(),orders1.getResult());
    }

    public Statistics getStatistics() {
        Integer confirmedNumber = orderMapper.getconfirmedNumber();
        Integer deliveryNumber = orderMapper.getDeliveryNumber();
        Integer toBeConfirmedNumber = orderMapper.getToBeConfirmedNumber();
        return new Statistics(confirmedNumber,deliveryNumber,toBeConfirmedNumber);
    }

    public Order getDetails(Long id) {
        Order order = orderMapper.gteDEtails(id);
        List<OrderDetail> orderDetails = orderDetailMapper.getOrderDetails(id);
        order.setOrderDetailList(orderDetails);
        return order;
    }

    public void cancel(OrderCancelDTO orderCancelDTO) {
        orderMapper.cancel(orderCancelDTO);
    }

    public void complete(Long id) {
        orderMapper.complete(id);
    }

    public void rejection(OrderRejectionDTO orderRejectionDTO) {
        orderMapper.rejection(orderRejectionDTO);
    }

    public void confirm(OrderConfirmDTO orderConfirmDTO) {
        orderMapper.confirm(orderConfirmDTO.getId());
    }

    public void delivery(Long id) {
        orderMapper.delivery(id);
    }
}
