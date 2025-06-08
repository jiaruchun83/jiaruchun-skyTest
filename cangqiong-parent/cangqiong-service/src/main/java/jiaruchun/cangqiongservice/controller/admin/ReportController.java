package jiaruchun.cangqiongservice.controller.admin;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jiaruchun.cangqiongservice.mapper.OrderMapper;
import jiaruchun.cangqiongservice.mapper.UserMapper;
import jiaruchun.cangqiongservice.service.admin.ReportService;
import jiaruchun.pojo.Result;
import jiaruchun.pojo.admin.dto.ExcellREportDetailDTO;
import jiaruchun.pojo.admin.vo.ReportOrderStatisticsVO;
import jiaruchun.pojo.admin.vo.ReportTop10VO;
import jiaruchun.pojo.admin.vo.ReportTurnoverStatisticsVO;
import jiaruchun.pojo.admin.vo.ReportUserStatisticsVO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/report")
public class ReportController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/top10")
    public Result getTop10(@RequestParam LocalDate begin,
                           @RequestParam LocalDate end){
        ReportTop10VO top10 = reportService.getTop10(begin, end);
        return Result.success(top10);
    }

    @GetMapping("/userStatistics")
    public Result getUserStatistics(@RequestParam LocalDate begin,
                                    @RequestParam LocalDate end){
        ReportUserStatisticsVO userStatistics = reportService.getUserStatistics(begin, end);
        return Result.success(userStatistics);
    }

    @GetMapping("/turnoverStatistics")
    public Result getTurnoverStatistics(@RequestParam LocalDate begin,
                                        @RequestParam LocalDate end){
        ReportTurnoverStatisticsVO turnoverStatistics = reportService.getTurnoverStatistics(begin, end);
        return Result.success(turnoverStatistics);
    }

    @GetMapping("/ordersStatistics")
    public Result getOrderStatistics(@RequestParam LocalDate begin,
                                     @RequestParam LocalDate end){
        ReportOrderStatisticsVO orderStatistics = reportService.getOrderStatistics(begin, end);
        return Result.success(orderStatistics);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("运营数据");
            //设置日期范围，end为昨天
            LocalDate end = LocalDate.now().minusDays(1);
            //begin为end的前30天
            LocalDate begin = end.minusDays(30);

            // 样式定义
            XSSFCellStyle titleStyle = workbook.createCellStyle();
            XSSFFont titleFont = workbook.createFont();
            titleFont.setFontHeightInPoints((short) 14);
            titleFont.setBold(true);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER); // 水平居中
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直居中
            titleStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleStyle.setBorderBottom(BorderStyle.THIN);

            XSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER); // 水平居中
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直居中
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            XSSFCellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setAlignment(HorizontalAlignment.CENTER); // 水平居中
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            // === 概览标题行 ===
            Row titleRow = sheet.createRow(0);
            titleRow.setHeightInPoints(25);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("概览数据");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

            // === 概览指标行1 ===
            Row row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue("营业额");
            row1.createCell(1).setCellValue("订单完成率");
            row1.createCell(2).setCellValue("新增用户数");
            row1.createCell(3).setCellValue("有效订单");
            row1.createCell(4).setCellValue("平均客单价");
            // === 概览指标行2 ===
            Row row2 = sheet.createRow(2);
            // === 示例：模拟数据填充 ===
            double totalRevenue = orderMapper.getTotalRevenue(begin, end);//
            double orderRate = reportService.getOrderStatistics(begin,end).getOrderCompletionRate();// 示例订单完成率
            int newUsers = userMapper.getAllNewUserCount(begin, end);
            int validOrders = orderMapper.getAllOrderNumber(begin,end);// 示例有效订单数
            double avgOrder = orderMapper.getAvgOrder(begin,end);// 示例平均客单价;

            row2.createCell(0).setCellValue(totalRevenue);
            //保留两位小数
            row2.createCell(1).setCellValue(new DecimalFormat("0.00").format(orderRate * 100) + "%");
            row2.createCell(2).setCellValue(newUsers);
            row2.createCell(3).setCellValue(validOrders);
            row2.createCell(4).setCellValue(avgOrder);

            // 应用样式
            for (Cell cell : row1) cell.setCellStyle(headerStyle);
            for (Cell cell : row2) cell.setCellStyle(headerStyle);

            // === 空行 + 明细标题 ===
            Row titleRow2 = sheet.createRow(4);
            Cell titleCell2 = titleRow2.createCell(0);
            titleCell2.setCellValue("明细数据");
            titleCell2.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));

            // === 明细表头 ===
            Row headerRow = sheet.createRow(5);
            String[] headers = {"日期", "营业额", "有效订单", "订单完成率", "平均客单价", "新增用户数"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
                sheet.setColumnWidth(i, 15 * 256);
            }

            // === 明细数据 ===

            List<ExcellREportDetailDTO> detailList = orderMapper.getDetailList(begin, end);

            for (int i = 0; i < detailList.size(); i++) {
                Row row = sheet.createRow(6 + i);

                ExcellREportDetailDTO detail = detailList.get(i);
                row.createCell(0).setCellValue(detail.getDate());
                row.createCell(1).setCellValue(String.valueOf(detail.getTurnover()));
                row.createCell(2).setCellValue(detail.getValidOrderCount());
                row.createCell(3).setCellValue(detail.getOrderCompletionRate());
                row.createCell(4).setCellValue(String.valueOf(detail.getAvgOrderAmount()));
                row.createCell(5).setCellValue(detail.getNewUserCount());
            }
            // === 输出 Excel 文件 ===
            String fileName = URLEncoder.encode("运营数据报表.xlsx", StandardCharsets.UTF_8);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
