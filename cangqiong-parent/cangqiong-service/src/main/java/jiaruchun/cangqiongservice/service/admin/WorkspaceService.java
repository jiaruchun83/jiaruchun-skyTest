package jiaruchun.cangqiongservice.service.admin;

import jiaruchun.cangqiongservice.mapper.OrderMapper;
import jiaruchun.cangqiongservice.mapper.SetMealMapper;
import jiaruchun.cangqiongservice.mapper.UserMapper;
import jiaruchun.pojo.admin.vo.BusinessDataVO;
import jiaruchun.pojo.admin.vo.OverviewDishesVO;
import jiaruchun.pojo.admin.vo.OverviewOrdersVO;
import jiaruchun.pojo.admin.vo.OverviewSetmealsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SetMealMapper setMealMapper;


    public BusinessDataVO businessData() {
        BusinessDataVO businessDataVO = new BusinessDataVO();
        businessDataVO.setNewUsers(userMapper.getNewUser());//新增用户
        businessDataVO.setOrderCompletionRate(orderMapper.getOrderCompleteRate());//订单完成率
        businessDataVO.setUnitPrice(orderMapper.getUnitPrice());//平均单价
        businessDataVO.setTurnover(orderMapper.getTurnover());//营业额
        businessDataVO.setValidOrderCount(orderMapper.getValidOrderCount());//有效订单数
        return businessDataVO;
    }

    public OverviewSetmealsVO overviewSetmeals() {
        return new OverviewSetmealsVO(
                setMealMapper.getDiscontinuedSetmeal(),
                setMealMapper.getSoldSetmeal()
        );
    }

    public OverviewDishesVO overviewDishes() {
        return new OverviewDishesVO(
                setMealMapper.getDiscontinuedDish(),
                setMealMapper.getSoldDish()
        );
    }

    public OverviewOrdersVO overviewOrders() {
        return new OverviewOrdersVO(
                orderMapper.getAllOrders(),
                orderMapper.getCancelledOrders(),
                orderMapper.getCompletedOrders(),
                orderMapper.getDeliveredOrders(),
                orderMapper.getWaitingOrders()
        );
    }
}
