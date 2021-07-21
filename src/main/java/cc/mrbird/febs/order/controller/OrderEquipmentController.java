package cc.mrbird.febs.order.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.factory.entity.Equipment;
import cc.mrbird.febs.factory.service.IEquipmentService;
import cc.mrbird.febs.order.entity.Order;
import cc.mrbird.febs.order.entity.OrderEquipment;
import cc.mrbird.febs.order.service.IOrderEquipmentService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zoybzo
 * @date 2021-07-20 20:37:05
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class OrderEquipmentController extends BaseController {

    private final IOrderEquipmentService orderEquipmentService;
    private final IEquipmentService equipmentService;
    private final IOrderService orderService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "orderEquipment")
    public String orderEquipmentIndex() {
        return FebsUtil.view("orderEquipment/orderEquipment");
    }

    @GetMapping("orderEquipment")
    @ResponseBody
    @RequiresPermissions("orderEquipment:list")
    public FebsResponse getAllOrderEquipments(OrderEquipment orderEquipment) {
        return new FebsResponse().success().data(orderEquipmentService.findOrderEquipments(orderEquipment));
    }

    @GetMapping("orderEquipment/list")
    @ResponseBody
    @RequiresPermissions("orderEquipment:list")
    public FebsResponse orderEquipmentList(QueryRequest request, OrderEquipment orderEquipment, HttpServletRequest httpServletRequest) {

        Map<String, Object> dataTable = getDataTable(this.orderEquipmentService.findOrderEquipments(request, orderEquipment));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增OrderEquipment", exceptionMessage = "新增OrderEquipment失败")
    @PostMapping("orderEquipment")
    @ResponseBody
    @RequiresPermissions("orderEquipment:add")
    public FebsResponse addOrderEquipment(@Valid OrderEquipment orderEquipment) {
        this.orderEquipmentService.createOrderEquipment(orderEquipment);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除OrderEquipment", exceptionMessage = "删除OrderEquipment失败")
    @GetMapping("orderEquipment/delete")
    @ResponseBody
    @RequiresPermissions("orderEquipment:delete")
    public FebsResponse deleteOrderEquipment(OrderEquipment orderEquipment) {
        this.orderEquipmentService.deleteOrderEquipment(orderEquipment);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改OrderEquipment", exceptionMessage = "修改OrderEquipment失败")
    @PostMapping("orderEquipment/update")
    @ResponseBody
    @RequiresPermissions("orderEquipment:update")
    public FebsResponse updateOrderEquipment(OrderEquipment orderEquipment) {
        OrderEquipment pre = orderEquipmentService.findByOrderId(String.valueOf(orderEquipment.getOrderId()));

        Equipment equipment = new Equipment();
        if (pre != null) {
            if (pre.getEquipmentId1() != null) {
                equipment.setEquipmentId(pre.getEquipmentId1());
                equipment.setStatus("0");
                this.equipmentService.updateEquipment(equipment);
            }
            if (pre.getEquipmentId2() != null) {
                equipment.setEquipmentId(pre.getEquipmentId2());
                equipment.setStatus("0");
                this.equipmentService.updateEquipment(equipment);
            }
            if (pre.getEquipmentId3() != null) {
                equipment.setEquipmentId(pre.getEquipmentId3());
                equipment.setStatus("0");
                this.equipmentService.updateEquipment(equipment);
            }
        }

        if (orderEquipment.getEquipmentId1() != null) {
            equipment.setEquipmentId(orderEquipment.getEquipmentId1());
            equipment.setStatus("1");
            this.equipmentService.updateEquipment(equipment);
        }
        if (orderEquipment.getEquipmentId2() != null) {
            equipment.setEquipmentId(orderEquipment.getEquipmentId2());
            equipment.setStatus("1");
            this.equipmentService.updateEquipment(equipment);
        }
        if (orderEquipment.getEquipmentId3() != null) {
            equipment.setEquipmentId(orderEquipment.getEquipmentId3());
            equipment.setStatus("1");
            this.equipmentService.updateEquipment(equipment);
        }

        if (pre != null) {
            pre.setEquipmentId1(orderEquipment.getEquipmentId1());
            pre.setEquipmentId2(orderEquipment.getEquipmentId2());
            pre.setEquipmentId3(orderEquipment.getEquipmentId3());
            orderEquipment = pre;
        }

        this.orderEquipmentService.updateOrderEquipment(orderEquipment);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改OrderEquipment", exceptionMessage = "导出Excel失败")
    @PostMapping("orderEquipment/excel")
    @ResponseBody
    @RequiresPermissions("orderEquipment:export")
    public void export(QueryRequest queryRequest, OrderEquipment orderEquipment, HttpServletResponse response) {
        List<OrderEquipment> orderEquipments = this.orderEquipmentService.findOrderEquipments(queryRequest, orderEquipment).getRecords();
        ExcelKit.$Export(OrderEquipment.class, response).downXlsx(orderEquipments, false);
    }
}
