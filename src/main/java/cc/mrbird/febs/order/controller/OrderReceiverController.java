package cc.mrbird.febs.order.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.order.entity.OrderReceiver;
import cc.mrbird.febs.order.service.IOrderReceiverService;
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
import java.util.List;
import java.util.Map;

/**
 * 订单收货人表 Controller
 *
 * @author zoybzo
 * @date 2021-07-15 18:55:48
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class OrderReceiverController extends BaseController {

    private final IOrderReceiverService orderReceiverService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "orderReceiver")
    public String orderReceiverIndex(){
        return FebsUtil.view("orderReceiver/orderReceiver");
    }

    @GetMapping("orderReceiver")
    @ResponseBody
    @RequiresPermissions("orderReceiver:list")
    public FebsResponse getAllOrderReceivers(OrderReceiver orderReceiver) {
        return new FebsResponse().success().data(orderReceiverService.findOrderReceivers(orderReceiver));
    }

    @GetMapping("orderReceiver/list")
    @ResponseBody
    @RequiresPermissions("orderReceiver:list")
    public FebsResponse orderReceiverList(QueryRequest request, OrderReceiver orderReceiver) {
        Map<String, Object> dataTable = getDataTable(this.orderReceiverService.findOrderReceivers(request, orderReceiver));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增OrderReceiver", exceptionMessage = "新增OrderReceiver失败")
    @PostMapping("orderReceiver")
    @ResponseBody
    @RequiresPermissions("orderReceiver:add")
    public FebsResponse addOrderReceiver(@Valid OrderReceiver orderReceiver) {
        this.orderReceiverService.createOrderReceiver(orderReceiver);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除OrderReceiver", exceptionMessage = "删除OrderReceiver失败")
    @GetMapping("orderReceiver/delete")
    @ResponseBody
    @RequiresPermissions("orderReceiver:delete")
    public FebsResponse deleteOrderReceiver(OrderReceiver orderReceiver) {
        this.orderReceiverService.deleteOrderReceiver(orderReceiver);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改OrderReceiver", exceptionMessage = "修改OrderReceiver失败")
    @PostMapping("orderReceiver/update")
    @ResponseBody
    @RequiresPermissions("orderReceiver:update")
    public FebsResponse updateOrderReceiver(OrderReceiver orderReceiver) {
        this.orderReceiverService.updateOrderReceiver(orderReceiver);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改OrderReceiver", exceptionMessage = "导出Excel失败")
    @PostMapping("orderReceiver/excel")
    @ResponseBody
    @RequiresPermissions("orderReceiver:export")
    public void export(QueryRequest queryRequest, OrderReceiver orderReceiver, HttpServletResponse response) {
        List<OrderReceiver> orderReceivers = this.orderReceiverService.findOrderReceivers(queryRequest, orderReceiver).getRecords();
        ExcelKit.$Export(OrderReceiver.class, response).downXlsx(orderReceivers, false);
    }
}
