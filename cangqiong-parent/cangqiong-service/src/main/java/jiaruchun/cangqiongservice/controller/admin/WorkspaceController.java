package jiaruchun.cangqiongservice.controller.admin;

import jiaruchun.cangqiongservice.service.admin.WorkspaceService;
import jiaruchun.pojo.Result;
import jiaruchun.pojo.admin.vo.BusinessDataVO;
import jiaruchun.pojo.admin.vo.OverviewDishesVO;
import jiaruchun.pojo.admin.vo.OverviewOrdersVO;
import jiaruchun.pojo.admin.vo.OverviewSetmealsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/workspace")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @GetMapping("/businessData")
    public Result businessData(){
        BusinessDataVO businessDataVO = workspaceService.businessData();
        return Result.success(businessDataVO);

    }

    @GetMapping("/overviewSetmeals")
    public Result overviewSetmeals(){
        OverviewSetmealsVO overviewSetmealsVO = workspaceService.overviewSetmeals();
        return Result.success(overviewSetmealsVO);
    }

    @GetMapping("/overviewDishes")
    public Result overviewDishes(){
        OverviewDishesVO overviewDishesVO = workspaceService.overviewDishes();
        return Result.success(overviewDishesVO);
    }

    @GetMapping("/overviewOrders")
    public Result overviewOrders(){
        OverviewOrdersVO overviewOrdersVO = workspaceService.overviewOrders();
        return Result.success(overviewOrdersVO);
    }
}
