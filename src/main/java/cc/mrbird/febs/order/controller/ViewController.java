package cc.mrbird.febs.order.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.Strings;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.order.entity.Order;
import cc.mrbird.febs.order.entity.OrderEquipment;
import cc.mrbird.febs.order.service.IOrderEquipmentService;
import cc.mrbird.febs.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @author Zoybzo
 */
@Controller("orderView")
@RequiredArgsConstructor
public class ViewController extends BaseController {

    private final IOrderService orderService;
    private final IOrderEquipmentService orderEquipmentService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "order/order")
    @RequiresPermissions("order:view")
    public String orderIndex() {
        return FebsUtil.view("order/order");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "order/bidList")
    @RequiresPermissions("order:view")
    public String winOrderIndex() {
        return FebsUtil.view("order/winOrder");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "order/equipment")
    @RequiresPermissions("order:view")
    public String orderEquipmentIndex() {
        return FebsUtil.view("order/orderEquipment");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "order/add")
    @RequiresPermissions("order:view")
    public String orderAddIndex() {
        return FebsUtil.view("order/orderAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "order/update/{orderId}")
    @RequiresPermissions("order:view")
    public String orderUpdateIndex(@PathVariable String orderId, Model model) {
        resolveOrderModel(orderId, model, false);
        return FebsUtil.view("order/orderUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "orderEquipment/update/{orderId}")
    @RequiresPermissions("order:view")
    public String orderEquipmentUpdateIndex(@PathVariable String orderId, Model model) {
        resolveOrderModel(orderId, model, false);
        resolveOrderEquipmentModel(orderId, model, false);
        return FebsUtil.view("order/orderEquipmentUpdate");
    }

    private void resolveOrderEquipmentModel(String orderId, Model model, boolean transform) {
        OrderEquipment orderEquipment = orderEquipmentService.findByOrderId(orderId);
        model.addAttribute("orderEquipment", orderEquipment);
    }

    @ControllerEndpoint(operation = "删除Order", exceptionMessage = "删除Order失败")
    @GetMapping("order/delete/{orderIds}")
    @ResponseBody
    @RequiresPermissions("order:delete")
    public FebsResponse deleteOrder(@NotBlank(message = "{required}") @PathVariable String orderIds, Order order, HttpServletRequest request) {
        if (this.orderService.checkOrderStatus(StringUtils.split(orderIds, Strings.COMMA))) {
            return new FebsResponse().fail();
        }
        for (String it : StringUtils.split(orderIds, Strings.COMMA)) {
            order.setOrderId(Long.valueOf(it));
            this.orderService.deleteOrder(order);
        }
        return new FebsResponse().success();
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "order/otherOrder")
    @RequiresPermissions("order:view")
    public String otherOrderIndex() {
        return FebsUtil.view("order/otherOrder");
    }

    private void resolveOrderModel(String orderId, Model model, boolean transform) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "order/send/{orderId}")
    public String orderSendIndex(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        if (!order.getStatus().equals("2")) {
            return FebsUtil.view("error/403");
        }
        resolveOrderModel(orderId, model, false);
        return FebsUtil.view("order/orderSend");
    }


}
