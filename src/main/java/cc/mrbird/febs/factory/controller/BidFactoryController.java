package cc.mrbird.febs.factory.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.factory.entity.BidFactory;
import cc.mrbird.febs.factory.service.IBidFactoryService;
import cc.mrbird.febs.factory.service.IFactoryService;
import cc.mrbird.febs.order.entity.Order;
import cc.mrbird.febs.order.service.IOrderService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 竞标工厂表 Controller
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:07
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class BidFactoryController extends BaseController {

    private final IBidFactoryService bidFactoryService;
    private final IOrderService orderService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "bidFactory")
    public String bidFactoryIndex() {
        return FebsUtil.view("bidFactory/bidFactory");
    }

    @GetMapping("bidFactory")
    @ResponseBody
    @RequiresPermissions("bidFactory:view")
    public FebsResponse getAllBidFactorys(BidFactory bidFactory) {
        return new FebsResponse().success().data(bidFactoryService.findBidFactorys(bidFactory));
    }

    @GetMapping("bidFactory/list")
    @ResponseBody
    @RequiresPermissions("bidFactory:view")
    public FebsResponse bidFactoryList(QueryRequest request, BidFactory bidFactory) {
        Map<String, Object> dataTable = getDataTable(this.bidFactoryService.findBidFactorys(request, bidFactory));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增BidFactory", exceptionMessage = "新增BidFactory失败")
    @PostMapping("bidFactory/add")
    @ResponseBody
    @RequiresPermissions("bidFactory:add")
    public FebsResponse addBidFactory(@Valid BidFactory bidFactory) {
        this.bidFactoryService.createBidFactory(bidFactory);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "选中BidFactory", exceptionMessage = "选中BidFactory失败")
    @PostMapping("bidFactory/select")
    @ResponseBody
    @RequiresPermissions("bidFactory:add")
    public FebsResponse selectBidFactory(@Valid BidFactory bidFactory) {
        bidFactory = bidFactoryService.findById(String.valueOf(bidFactory.getBidFactoryId()));
        Order order = orderService.findById(String.valueOf(bidFactory.getOrderId()));
        order.setStatus("2");
        order.setModifyTime(new Date());
        order.setSelectedFactoryId((bidFactory.getFactoryId()));
        this.orderService.updateOrder(order);
        return new FebsResponse().success();
    }


    @ControllerEndpoint(operation = "删除BidFactory", exceptionMessage = "删除BidFactory失败")
    @GetMapping("bidFactory/delete")
    @ResponseBody
    @RequiresPermissions("bidFactory:delete")
    public FebsResponse deleteBidFactory(BidFactory bidFactory) {
        this.bidFactoryService.deleteBidFactory(bidFactory);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改BidFactory", exceptionMessage = "修改BidFactory失败")
    @PostMapping("bidFactory/update")
    @ResponseBody
    @RequiresPermissions("bidFactory:update")
    public FebsResponse updateBidFactory(BidFactory bidFactory) {
        this.bidFactoryService.updateBidFactory(bidFactory);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改BidFactory", exceptionMessage = "导出Excel失败")
    @PostMapping("bidFactory/excel")
    @ResponseBody
    @RequiresPermissions("bidFactory:export")
    public void export(QueryRequest queryRequest, BidFactory bidFactory, HttpServletResponse response) {
        List<BidFactory> bidFactorys = this.bidFactoryService.findBidFactorys(queryRequest, bidFactory).getRecords();
        ExcelKit.$Export(BidFactory.class, response).downXlsx(bidFactorys, false);
    }
}
