package cc.mrbird.febs.factory.controller;

import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.factory.entity.BidFactory;
import cc.mrbird.febs.factory.entity.Equipment;
import cc.mrbird.febs.factory.entity.EquipmentType;
import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.factory.service.*;
import cc.mrbird.febs.order.entity.Order;
import cc.mrbird.febs.order.service.IOrderService;
import cc.mrbird.febs.system.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MrBird
 */
@Controller("factoryView")
@RequiredArgsConstructor
public class ViewController extends BaseController {

    private final IFactoryService factoryService;
    private final IUserFactoryService userFactoryService;
    private final IEquipmentTypeService equipmentTypeService;
    private final IEquipmentService equipmentService;
    private final IOrderService orderService;
    private final IBidFactoryService bidFactoryService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "factory/factory")
    @RequiresPermissions("factory:view")
    public String factoryIndex() {
        return FebsUtil.view("factory/factory");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "factory/add")
    @RequiresPermissions("factory:view")
    public String factoryAddIndex() {
        return FebsUtil.view("factory/factoryAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "factory/detail/{factoryName}")
    @RequiresPermissions("factory:view")
    public String FactoryDetail(@PathVariable String factoryName, Model model) {
        resolveFactoryModel(factoryName, model, false);
        return FebsUtil.view("factory/factoryDetail");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "factory/update/{factoryName}")
    @RequiresPermissions("factory:update")
    public String FactoryUpdate(@PathVariable String factoryName, Model model) {
        resolveFactoryModel(factoryName, model, true);
        return FebsUtil.view("factory/factoryUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "factory/equipmentType")
    @RequiresPermissions("equipmentType:view")
    public String equipmentTypeIndex() {
        return FebsUtil.view("factory/equipmentType/type");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "equipmentType/add")
    @RequiresPermissions("equipmentType:view")
    public String equipmentTypeAddIndex() {
        return FebsUtil.view("factory/equipmentType/typeAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "equipmentType/update/{equipmentTypeId}")
    @RequiresPermissions("equipmentType:view")
    public String equipmentTypeUpdateIndex(@PathVariable String equipmentTypeId, Model model) {
        resolveEquipmentTypeModel(equipmentTypeId, model, false);
        return FebsUtil.view("factory/equipmentType/typeUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "equipment/equipment")
    @RequiresPermissions("equipment:view")
    public String equipmentIndex() {
        return FebsUtil.view("equipment/equipment");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "equipment/add")
    @RequiresPermissions("equipment:add")
    public String equipmentAddIndex() {
        return FebsUtil.view("equipment/equipmentAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "equipment/update/{equipmentId}")
    @RequiresPermissions("equipment:update")
    public String equipmentUpdateIndex(@PathVariable String equipmentId, Model model) {
        resolveEquipmentModel(equipmentId, model, false);
        return FebsUtil.view("equipment/equipmentUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "equipment/detail/{equipmentId}")
    @RequiresPermissions("equipment:view")
    public String equipmentDetailndex(@PathVariable String equipmentId, Model model) {
        resolveEquipmentModel(equipmentId, model, false);
        return FebsUtil.view("equipment/equipmentDetail");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "equipment/rent/{equipmentId}")
    @RequiresPermissions("equipment:update")
    public String equipmentRentIndex(@PathVariable String equipmentId, Model model) {
        resolveEquipmentModel(equipmentId, model, false);
        Equipment equipment = ((Equipment) (model.getAttribute("equipment")));
        if (!equipment.getOwnedFactory().equals(equipment.getUsingFactory()))
            return FebsUtil.view("error/equipmentCannotBeReturn");
        return FebsUtil.view("equipment/equipmentRent");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "equipment/return/{equipmentId}")
    @RequiresPermissions("equipment:update")
    public String equipmentReturnIndex(@PathVariable String equipmentId, Model model) {
        resolveEquipmentModel(equipmentId, model, false);
        return FebsUtil.view("equipment/equipmentReturn");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "equipment/rentInfo")
    @RequiresPermissions("equipment:view")
    public String equipmentRentInfoIndex() {
        return FebsUtil.view("equipment/rentInfo");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "bidFactory/bid/{orderId}")
    public String orderBidIndex(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        if (!order.getStatus().equals("1")) {
            return FebsUtil.view("error/403");
        }
        BidFactory bidFactory = new BidFactory();
        bidFactory.setOrderId(Long.parseLong(orderId));
        model.addAttribute("bidFactory", bidFactory);
        return FebsUtil.view("factory/bidFactoryAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "bidFactory/bidFactory")
    @RequiresPermissions("bidFactory:view")
    public String bidFactoryIndex() {
        return FebsUtil.view("factory/bidFactory");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "bidFactory/select/{bidFactoryId}")
    @RequiresPermissions("bidFactory:view")
    public String bidFactorySelectIndex(@PathVariable String bidFactoryId, Model model, HttpServletRequest request) {
        BidFactory bidFactory = bidFactoryService.findById(bidFactoryId);
        Order order = orderService.findById(String.valueOf(bidFactory.getOrderId()));
        if (order.getUserId().equals(((User) (request.getSession().getAttribute("user"))).getUserId()) && order.getStatus().equals("1")) {
            resolveBidFactoryModel(bidFactoryId, model, false);
            return FebsUtil.view("factory/bidFactorySelect");
        }
        return FebsUtil.view("error/403");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "bidFactory/update/{bidFactoryId}")
    @RequiresPermissions("factory:update")
    public String bidFactoryUpdate(@PathVariable String bidFactoryId, Model model) {
        resolveBidFactoryModel(bidFactoryId, model, true);
        return FebsUtil.view("factory/bidFactoryUpdate");
    }

    private void resolveBidFactoryModel(String bidFactoryId, Model model, boolean transform) {
        BidFactory bidFactory = bidFactoryService.findById(bidFactoryId);
        Order order = orderService.findById(String.valueOf(bidFactory.getOrderId()));
        model.addAttribute("bidFactory", bidFactory);
        model.addAttribute("order", order);
    }

    private void resolveOrderModel(String orderId, Model model, boolean transform) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
    }

    private void resolveEquipmentModel(String equipmentId, Model model, Boolean transform) {
        Equipment equipment = equipmentService.findById(equipmentId);
        model.addAttribute("equipment", equipment);
    }

    private void resolveEquipmentTypeModel(String equipmentTypeId, Model model, Boolean transform) {
        EquipmentType equipmentType = equipmentTypeService.findById(equipmentTypeId);
        model.addAttribute("equipmentType", equipmentType);
    }

    private void resolveFactoryModel(String factoryName, Model model, Boolean transform) {
        Factory factory = factoryService.findByName(factoryName);
        String userId = userFactoryService.findByFactoryId(String.valueOf(factory.getFactoryId()));
        factory.setUserId(userId);
        model.addAttribute("factory", factory);
    }

}
