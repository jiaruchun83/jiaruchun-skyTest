package jiaruchun.cangqiongservice.service.user;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import jiaruchun.cangqiongservice.mapper.OrderDetailMapper;
import jiaruchun.cangqiongservice.mapper.OrderMapper;
import jiaruchun.cangqiongservice.mapper.ShoppingCartMapper;
import jiaruchun.cangqiongservice.mapper.UserMapper;
import jiaruchun.cangqiongservice.websocket.WebSocketServer;
import jiaruchun.common.utils.ThreadLocalUtil;
import jiaruchun.pojo.Order;
import jiaruchun.pojo.PageResult;
import jiaruchun.pojo.user.dto.OrderPayDTO;
import jiaruchun.pojo.user.dto.UserSubmitOrderDTO;
import jiaruchun.pojo.user.entity.OrderDetail;
import jiaruchun.pojo.user.entity.ShoppingCart;
import jiaruchun.pojo.user.vo.OrderPayResultVO;
import jiaruchun.pojo.user.vo.SubmitOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class UserOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private WebSocketServer webSocketServer;

    @Transactional(rollbackFor = Exception.class)
    public SubmitOrderVO submit(UserSubmitOrderDTO userSubmitOrderDTO) {
        Order order = new Order();

        //地址
        order.setAddressBookId(Long.valueOf(userSubmitOrderDTO.getAddressBookId()));
        order.setAddress(addressBookService.concatAddress(addressBookService.reById(userSubmitOrderDTO.getAddressBookId())));

        //配送时间，单纯增加一个小时，以后进行完善处理
        if(userSubmitOrderDTO.getDeliveryStatus() == 1){//立即配送
            //当前下单时间加一小时
            order.setEstimatedDeliveryTime(userSubmitOrderDTO.getEstimatedDeliveryTime());
        }else{//自己选择
            order.setEstimatedDeliveryTime(userSubmitOrderDTO.getEstimatedDeliveryTime());
        }

        //餐具数量
        if(userSubmitOrderDTO.getTablewareStatus() == 1){//依据参量供应,就是看买的东西量的多少,我自己用根号n,向上取整来计算
            Integer number = shoppingCartMapper.reShoppingCartCount(ThreadLocalUtil.get());
            order.setTablewareNumber((int)Math.ceil(Math.sqrt(number)));
        }else{//自己选择
            order.setTablewareNumber(userSubmitOrderDTO.getTablewareNumber());
        }

        order.setDeliveryStatus(userSubmitOrderDTO.getDeliveryStatus());//配送方式
        order.setPayMethod(userSubmitOrderDTO.getPayMethod());//支付方式
        order.setAmount(userSubmitOrderDTO.getAmount().add(BigDecimal.valueOf(userSubmitOrderDTO.getPackAmount())));//总金额
        order.setPackAmount(userSubmitOrderDTO.getPackAmount());//打包费
        order.setRemark(userSubmitOrderDTO.getRemark());//备注
        order.setStatus(1);//还在待付款状态
        order.setOrderTime(LocalDateTime.now());//下单时间
        order.setCheckoutTime(null);//现在还未付款
        order.setPhone(addressBookService.reById(userSubmitOrderDTO.getAddressBookId()).getPhone());
        order.setUserName(userMapper.reById(ThreadLocalUtil.get()).getName());//用户名
        order.setUserId(ThreadLocalUtil.get());
        order.setPayStatus(0);//还未支付
        order.setTablewareStatus(userSubmitOrderDTO.getTablewareStatus());
        order.setConsignee(addressBookService.reById(userSubmitOrderDTO.getAddressBookId()).getConsignee());//收货人
        //还没有取消原因，拒单原因，订单取消时间，

        // 使用时间戳生成订单号
        long timestamp = System.currentTimeMillis();
        order.setNumber(String.valueOf(timestamp));

        //送达时间，自选的就是自选时间,立即我这里设成 加1个小时
        if(userSubmitOrderDTO.getDeliveryStatus() == 0){
            order.setDeliveryTime(userSubmitOrderDTO.getEstimatedDeliveryTime().minusHours(1));
        }else{
            order.setDeliveryTime(userSubmitOrderDTO.getEstimatedDeliveryTime());
        }

        orderMapper.add(order);//回显

        //添加单明细,每种菜的信息,刚好对应一个购物车
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.reShoppingCart(ThreadLocalUtil.get());
        List<OrderDetail> orderDetails1 = new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCarts) {
            OrderDetail orderDetail = new OrderDetail(null, shoppingCart.getName(), shoppingCart.getImage(), order.getId(), shoppingCart.getDishId(), shoppingCart.getSetmealId(), shoppingCart.getDishFlavor(), shoppingCart.getNumber(), shoppingCart.getAmount());
            orderDetails1.add(orderDetail);
        }

        order.setOrderDetailList(orderDetails1);
        orderDetailMapper.add(order.getOrderDetailList());

        //清理购物车
        shoppingCartMapper.removeAll(ThreadLocalUtil.get());
        return new SubmitOrderVO(order.getId(), order.getAmount(),order.getNumber(),order.getOrderTime());
    }

    public Order getOrderDetail(Long id) {
        List<OrderDetail> orderDetails = orderDetailMapper.getOrderDetails(id);

        Order order = orderMapper.getOrder(id);

        order.setOrderDetailList(orderDetails);

        return order;
    }


    public OrderPayResultVO payment(OrderPayDTO orderPayDTO) {
        //openid
        String openid = userMapper.reById(ThreadLocalUtil.get()).getOpenid();

        /*调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = weChatPayUtil.pay(
                ordersPaymentDTO.getOrderNumber(), //商户订单号
                new BigDecimal(0.01), //支付金额，单位 元
                "苍穹外卖订单", //商品描述
                openid //微信用户的openid
        );

        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new OrderBusinessException("该订单已支付");
        }*/

        //更新订单,更新订单的状态、支付状态、结账时间
        orderMapper.upStatusOnPaged(orderPayDTO.getOrderNumber());

        //向管理端发来单提醒
        HashMap<String, Object> massgeHashMap = new HashMap<>();
        /*前端对服务器发的消息规定，约定服务端发送给客户端浏览器的数据格式为JSON，字段包括：type(1来单，2催单),orderld,content(消息内容)*/
        massgeHashMap.put("type",1);
        massgeHashMap.put("orderld",orderMapper.getOrderByOrderNumber(orderPayDTO.getOrderNumber()).getId());
        massgeHashMap.put("content","您有新的订单，订单号" + orderPayDTO.getOrderNumber());
        String json = new Gson().toJson(massgeHashMap);
        log.info("向管理端发送来单消息");
        webSocketServer.sendToAllClient(json);

        //返回预计时间
        LocalDateTime estimatedDeliveryTime = orderMapper.getOrderByOrderNumber(orderPayDTO.getOrderNumber()).getEstimatedDeliveryTime();
        return new OrderPayResultVO(estimatedDeliveryTime);
    }

    public PageResult<Order> getAllOrder(Integer page, Integer pageSize, Integer status) {
        PageHelper.startPage(page,pageSize);
        List<Order> allOrder = orderMapper.getAllOrder(status);
        for (Order order : allOrder) {
            order.setOrderDetailList(orderDetailMapper.getOrderDetails(order.getId()));
        }
        Page<Order> allOrder1 = (Page<Order>) allOrder;
        return new PageResult<>(allOrder1.getTotal(),allOrder1.getResult());
    }

    /*催单*/
    public void reminder(Long id) {
        String orderNumber = orderMapper.getOrder(id).getNumber();
        //向管理端发送消息
        log.info("向管理端发起催单");
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("type",2);//为催单
        stringObjectHashMap.put("orderld",id);
        stringObjectHashMap.put("content","单号" + orderNumber + "发来催单");
        String json = new Gson().toJson(stringObjectHashMap);
        webSocketServer.sendToAllClient(json);
    }
}
