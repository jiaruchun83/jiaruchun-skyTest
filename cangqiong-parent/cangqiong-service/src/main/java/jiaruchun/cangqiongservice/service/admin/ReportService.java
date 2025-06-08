package jiaruchun.cangqiongservice.service.admin;

import jiaruchun.cangqiongservice.mapper.OrderDetailMapper;
import jiaruchun.cangqiongservice.mapper.OrderMapper;
import jiaruchun.cangqiongservice.mapper.UserMapper;
import jiaruchun.pojo.admin.vo.ReportOrderStatisticsVO;
import jiaruchun.pojo.admin.vo.ReportTop10VO;
import jiaruchun.pojo.admin.vo.ReportTurnoverStatisticsVO;
import jiaruchun.pojo.admin.vo.ReportUserStatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private UserMapper userMapper;

    public ReportTop10VO getTop10(LocalDate begin, LocalDate end) {
        String nameList = orderDetailMapper.getTop10Name(begin, end);
        String numberList = orderDetailMapper.getTop10Number(begin, end);
        return new ReportTop10VO(nameList, numberList);
    }

    public ReportUserStatisticsVO getUserStatistics(LocalDate begin, LocalDate end) {
        //制作日期列表，从begin到end，用逗号分隔
        String dateList = Stream.iterate(begin, date -> date.isBefore(end) || date.isEqual(end), date -> date.plusDays(1))
                .map(date -> date.format(DateTimeFormatter.ISO_DATE))
                .collect(Collectors.joining(","));//当日日期列表，用逗号分隔
        String newUserList = userMapper.getNewUserList(begin, end);//当日新增用户数量，用逗号分隔
        String totalUserList = userMapper.getTotalUserList(begin, end);//当日总用户数量，用逗号分隔
        return new ReportUserStatisticsVO(dateList, newUserList, totalUserList);
    }

    public ReportTurnoverStatisticsVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        //制作日期列表，从begin到end，用逗号分隔
        String dateList = Stream.iterate(begin, date -> date.isBefore(end) || date.isEqual(end), date -> date.plusDays(1))
                .map(date -> date.format(DateTimeFormatter.ISO_DATE))
                .collect(Collectors.joining(","));//当日日期列表，用逗号分隔
        String turnoverList = orderMapper.getTurnoverList(begin, end);//当日营业额，用逗号分隔
        return new ReportTurnoverStatisticsVO(dateList, turnoverList);
    }

    public ReportOrderStatisticsVO getOrderStatistics(LocalDate begin, LocalDate end) {
        String dateList = Stream.iterate(begin, date -> date.isBefore(end) || date.isEqual(end), date -> date.plusDays(1))
               .map(date -> date.format(DateTimeFormatter.ISO_DATE))
               .collect(Collectors.joining(","));
        Integer totalOrderCount = orderMapper.getTotalOrderCount(begin, end);
        Integer validOrderCount = orderMapper.getAllValidOrderCount(begin, end);
        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            //保留两位小数
            orderCompletionRate = (double) validOrderCount / totalOrderCount;
        }
        String orderCountList = orderMapper.getOrderCountList(begin, end);
        String validOrderCountList = orderMapper.getValidOrderCountList(begin, end);
        return new ReportOrderStatisticsVO(dateList, validOrderCountList, orderCountList, orderCompletionRate, totalOrderCount, validOrderCount);
    }

}
