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
import cc.mrbird.febs.system.entity.User;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.mrbird.febs.system.service.IUserRoleService;

/**
 * 订单表 Controller
 *
 * @author zoybzo
 * @date 2021-07-15 18:55:40
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class OrderController extends BaseController {

    private final IOrderService orderService;
    private final IUserRoleService userRoleService;
    private final IOrderEquipmentService orderEquipmentService;
    private final IEquipmentService equipmentService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "order")
    public String orderIndex() {
        return FebsUtil.view("order/order");
    }

    @GetMapping("order")
    @ResponseBody
    @RequiresPermissions("order:view")
    public FebsResponse getAllOrders(Order order) {
        return new FebsResponse().success().data(orderService.findOrders(order));
    }

    @GetMapping("order/list")
    @ResponseBody
    @RequiresPermissions("order:view")
    public FebsResponse orderList(QueryRequest request, Order order, HttpServletRequest httpServletRequest) {
        order.setUserId(((User) (httpServletRequest.getSession().getAttribute("user"))).getUserId());
        Map<String, Object> dataTable = getDataTable(this.orderService.findOrders(request, order));
        return new FebsResponse().success().data(dataTable);
    }

    @GetMapping("order/otherOrderList")
    @ResponseBody
    @RequiresPermissions("order:view")
    public FebsResponse otherOrderList(QueryRequest request, Order order, HttpServletRequest httpServletRequest) {
        order.setUserId(((User) (httpServletRequest.getSession().getAttribute("user"))).getUserId());
        Map<String, Object> dataTable = getDataTable(this.orderService.findOtherOrders(request, order));
        return new FebsResponse().success().data(dataTable);
    }

    @GetMapping("order/myList")
    @ResponseBody
    @RequiresPermissions("order:view")
    public FebsResponse myOrderList(QueryRequest request, Order order, HttpServletRequest httpServletRequest) {
        order.setSelectedUserId(((User) (httpServletRequest.getSession().getAttribute("user"))).getUserId());
        order.setStatus("2");
        Map<String, Object> dataTable = getDataTable(this.orderService.findMyOrders(request, order));
        return new FebsResponse().success().data(dataTable);
    }

    @GetMapping("order/winOrderList/{winUserId}")
    @ResponseBody
    @RequiresPermissions("order:view")
    public FebsResponse orderList(@PathVariable String winUserId, QueryRequest request, Order order) {
        Long role = userRoleService.findRoleIdByUserId(winUserId);
        if (role != 1L) {
            order.setSelectedUserId(Long.parseLong(winUserId));
        } else order.setSelectedUserId(null);

        Map<String, Object> dataTable = getDataTable(this.orderService.findOrders(request, order));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增Order", exceptionMessage = "新增Order失败")
    @PostMapping("order/orderAdd")
    @ResponseBody
    @RequiresPermissions("order:add")
    public FebsResponse addFactory(@Valid Order order, HttpServletRequest request) {
        order.setUserId(((User) request.getSession().getAttribute("user")).getUserId());
        order.setCreateTime(new Date());
        order.setStatus("0");
        this.orderService.createOrder(order);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "新增Order", exceptionMessage = "新增Order失败")
    @PostMapping("order")
    @ResponseBody
    @RequiresPermissions("order:add")
    public FebsResponse addOrder(@Valid Order order) {
        this.orderService.createOrder(order);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除Order", exceptionMessage = "删除Order失败")
    @GetMapping("order/delete")
    @ResponseBody
    @RequiresPermissions("order:delete")
    public FebsResponse deleteOrder(Order order) {
        this.orderService.deleteOrder(order);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Order", exceptionMessage = "修改Order失败")
    @PostMapping("order/update")
    @ResponseBody
    @RequiresPermissions("order:update")
    public FebsResponse updateOrder(Order order) {
        Order tmp = orderService.findById(String.valueOf(order.getOrderId()));
        if (Long.parseLong(order.getStatus()) < Long.parseLong(tmp.getStatus())) {
            return new FebsResponse().fail();
        }
        if (Long.parseLong(tmp.getStatus()) >= 3L && Long.parseLong(tmp.getStatus()) <= Long.parseLong(order.getStatus())) {
            if (order.getProductId().equals(tmp.getProductId()) && order.getQuantity().equals(tmp.getQuantity())) {
                order.setModifyTime(new Date());
                this.orderService.updateOrder(order);
                return new FebsResponse().success();
            }
        }

        if (order.getSelectedFactoryId() == null && Long.parseLong(order.getStatus()) > 1L) {
            return new FebsResponse().fail();
        }

        order.setModifyTime(new Date());
        this.orderService.updateOrder(order);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Order", exceptionMessage = "修改Order失败")
    @PostMapping("order/send")
    @ResponseBody
    @RequiresPermissions("order:update")
    public FebsResponse sendOrder(Order order) {
        order = orderService.findById(String.valueOf(order.getOrderId()));
        OrderEquipment orderEquipment = new OrderEquipment();
        orderEquipment.setOrderId(order.getOrderId());
        order.setStatus("3");
        order.setModifyTime(new Date());

        List<OrderEquipment> orderEquipmentList = orderEquipmentService.findOrderEquipments(orderEquipment);
        for (OrderEquipment it : orderEquipmentList) {
            if (it.getEquipmentId1() != null) {
                Equipment equipment = equipmentService.findById(String.valueOf(it.getEquipmentId1()));
                if (equipment.getStatus().equals("1")) {
                    equipment.setStatus("0");
                    equipmentService.updateEquipment(equipment);
                    orderEquipmentService.deleteOrderEquipment(orderEquipment);
                }
            }
            if (it.getEquipmentId2() != null) {
                Equipment equipment = equipmentService.findById(String.valueOf(it.getEquipmentId2()));
                if (equipment.getStatus().equals("1")) {
                    equipment.setStatus("0");
                    equipmentService.updateEquipment(equipment);
                    orderEquipmentService.deleteOrderEquipment(orderEquipment);
                }
            }
            if (it.getEquipmentId3() != null) {
                Equipment equipment = equipmentService.findById(String.valueOf(it.getEquipmentId3()));
                if (equipment.getStatus().equals("1")) {
                    equipment.setStatus("0");
                    equipmentService.updateEquipment(equipment);
                    orderEquipmentService.deleteOrderEquipment(orderEquipment);
                }
            }
        }

        this.orderService.updateOrder(order);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Order", exceptionMessage = "导出Excel失败")
    @PostMapping("order/excel")
    @ResponseBody
    @RequiresPermissions("order:export")
    public void export(QueryRequest queryRequest, Order order, HttpServletResponse response) {
        List<Order> orders = this.orderService.findOrders(queryRequest, order).getRecords();
        ExcelKit.$Export(Order.class, response).downXlsx(orders, false);
    }
}
